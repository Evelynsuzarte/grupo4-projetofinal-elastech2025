document.addEventListener("DOMContentLoaded", () => {
  const usuarioLogado = localStorage.getItem("usuarioLogado");

  if (!usuarioLogado) {
    setTimeout(() => {
      alert("Você precisa estar logado para acessar o portal do professor.");
      window.location.href = "login.html";
    }, 300);
    return;
  }

  const usuario = JSON.parse(usuarioLogado);

  const nomeUsuarioSpan = document.getElementById("nomeUsuario");
  const botaoEntrar = document.getElementById("botaoEntrar");
  const usuarioInfo = document.getElementById("usuarioInfo");

  if (nomeUsuarioSpan && botaoEntrar && usuarioInfo) {
    nomeUsuario.textContent = `Olá, ${usuario.nome}!`;
    botaoEntrar.style.display = "none";
    usuarioInfo.style.display = "flex";

    document.getElementById("botaoSair").addEventListener("click", () => {
      localStorage.removeItem("usuarioLogado");
      window.location.href = "login.html";
    });
  }

  const tabelaCursos = document.getElementById("tabela-cursos");
  const formCurso = document.getElementById("formCurso");

  carregarCursos();

  function carregarCursos() {
    fetch(`http://localhost:8080/cursos/professor/${usuario.id}`)
      .then(resp => {
        if (!resp.ok) throw new Error();
        return resp.json();
      })
      .then(cursos => {
        tabelaCursos.innerHTML = "";

        if (!cursos.length) {
          tabelaCursos.innerHTML = "<tr><td colspan='4'>Nenhum curso cadastrado.</td></tr>";
          return;
        }

        cursos.forEach(curso => {
          const tr = document.createElement("tr");
          tr.innerHTML = `
            <td>${curso.titulo}</td>
            <td>${curso.descricao}</td>
            <td>
              <button class="botao botao-borda" onclick="verAlunos(${curso.id})">Ver alunos</button>
              <button class="botao botao-borda" onclick="editarCurso(${curso.id}, '${curso.titulo}', '${curso.descricao}')">Editar</button>
            </td>
          `;
          tabelaCursos.appendChild(tr);
        });
      })
      .catch(() => {
        tabelaCursos.innerHTML = "<tr><td colspan='4'>Erro ao carregar cursos.</td></tr>";
      });
  }

  formCurso.addEventListener("submit", e => {
    e.preventDefault();
    const titulo = document.getElementById("titulo").value.trim();
    const descricao = document.getElementById("descricao").value.trim();

    if (!titulo || !descricao) {
      alert("Preencha todos os campos!");
      return;
    }

    fetch("http://localhost:8080/cursos/adicionar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ titulo, descricao, professorId: usuario.id }),
    })
      .then(resp => {
        if (!resp.ok) throw new Error();
        alert("Curso criado com sucesso!");
        formCurso.reset();
        carregarCursos();
      })
      .catch(() => alert("Erro ao criar curso."));
  });

  window.editarCurso = (id, titulo, descricao) => {
    const novoTitulo = prompt("Novo título:", titulo);
    const novaDescricao = prompt("Nova descrição:", descricao);
    if (!novoTitulo || !novaDescricao) return;

    fetch(`http://localhost:8080/cursos/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ titulo: novoTitulo, descricao: novaDescricao }),
    })
      .then(resp => {
        if (!resp.ok) throw new Error();
        alert("Curso atualizado!");
        carregarCursos();
      })
      .catch(() => alert("Erro ao atualizar curso."));
  };

  window.verAlunos = id => {
    fetch(`http://localhost:8080/matriculas/curso/${id}`)
      .then(resp => {
        if (!resp.ok) throw new Error();
        return resp.json();
      })
      .then(alunos => {
        if (!alunos.length) {
          alert("Nenhum aluno matriculado.");
          return;
        }
        const lista = alunos.map(a => `- ${a.aluno.nome}`).join("\n");
        alert(`Alunos matriculados:\n\n${lista}`);
      })
      .catch(() => alert("Erro ao carregar alunos."));
  };
});

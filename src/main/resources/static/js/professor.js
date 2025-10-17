document.addEventListener("DOMContentLoaded", () => {
  const usuarioLogado = JSON.parse(localStorage.getItem("usuarioLogado"));

  // --- Proteção de acesso ---
  if (!usuarioLogado || usuarioLogado.perfil !== "PROFESSOR") {
    alert("Você precisa estar logado como professor para acessar o portal.");
    window.location.href = "login.html";
    return;
  }

  // ---- Elementos da página ----
  const tabelaCursos = document.getElementById("tabela-cursos");
  const formCurso = document.getElementById("cursoForm");
  const modalCurso = document.getElementById("modalCurso");
  const modalTitulo = document.getElementById("modalTitulo");
  const novoCursoBtn = document.getElementById("novoCursoBtn");
  const cancelarBtn = document.getElementById("cancelarBtn");

  // Cabeçalho
  const nomeUsuario = document.getElementById("nomeUsuario");
  const botaoEntrar = document.getElementById("botaoEntrar");
  const usuarioInfo = document.getElementById("usuarioInfo");
  const botaoSair = document.getElementById("botaoSair");

  // ---- Atualiza cabeçalho ----
  botaoEntrar.style.display = "none";
  usuarioInfo.style.display = "flex";
  nomeUsuario.textContent = `Olá, ${usuarioLogado.nome}!`;

  botaoSair.addEventListener("click", () => {
    localStorage.removeItem("usuarioLogado");
    window.location.href = "index.html";
  });

  // ---- Função: carregar cursos ----
  function carregarCursos() {
    tabelaCursos.innerHTML = `<tr><td colspan="4">Carregando...</td></tr>`;

    fetch(`http://localhost:8080/cursos/professor/${usuarioLogado.id}`)
      .then(resp => {
        if (!resp.ok) throw new Error("Erro ao carregar cursos");
        return resp.json();
      })
      .then(cursos => {
        tabelaCursos.innerHTML = "";

        if (!cursos.length) {
          tabelaCursos.innerHTML = `<tr><td colspan="4">Nenhum curso cadastrado.</td></tr>`;
          return;
        }

        cursos.forEach(curso => {
          const tr = document.createElement("tr");
          tr.innerHTML = `
            <td>${curso.titulo}</td>
            <td>${curso.descricao}</td>
            <td>
              <button class="botao botao-borda"
                      data-id="${curso.id}"
                      data-titulo="${curso.titulo}"
                      data-descricao="${curso.descricao}"
                      data-imagem="${curso.caminhoImagem || ''}"
                      data-acao="editar">Editar</button>
              <button class="botao botao-borda"
                      data-id="${curso.id}"
                      data-acao="alunos">Ver Alunos</button>
            </td>
          `;
          tabelaCursos.appendChild(tr);
        });
      })
      .catch(() => {
        tabelaCursos.innerHTML = `<tr><td colspan="4">Erro ao carregar cursos.</td></tr>`;
      });
  }

  // ---- Função: criar ou editar curso ----
  formCurso.addEventListener("submit", async (e) => {
    e.preventDefault();

    const id = document.getElementById("cursoId").value;
    const titulo = document.getElementById("titulo").value.trim();
    const descricao = document.getElementById("descricao").value.trim();
    const caminhoImagem = document.getElementById("caminhoImagem").value.trim();

    if (!titulo || !descricao || !caminhoImagem) {
      alert("Preencha todos os campos!");
      return;
    }

    const url = id
      ? `http://localhost:8080/cursos/${id}`
      : `http://localhost:8080/cursos/adicionar`;

    const metodo = id ? "PUT" : "POST";

    const cursoData = {
      titulo,
      descricao,
      caminhoImagem,
      professorId: Number(usuarioLogado.id)
    };


    try {
      const resp = await fetch(url, {
        method: metodo,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(cursoData)
      });

      const text = await resp.text();

      if (!resp.ok) {
        throw new Error(`Erro ao salvar curso: ${text}`);
      }

      alert(id ? "Curso atualizado com sucesso!" : "Curso criado com sucesso!");
      formCurso.reset();
      modalCurso.classList.remove("ativo");
      carregarCursos();
    } catch (err) {
      alert("Erro ao salvar o curso. Verifique se todos os campos estão corretos.");
    }
  });

  // ---- Botão Novo Curso ----
  novoCursoBtn.addEventListener("click", () => {
    modalTitulo.textContent = "Criar Novo Curso";
    formCurso.reset();
    document.getElementById("cursoId").value = "";
    modalCurso.classList.add("ativo");
  });

  // ---- Botão Cancelar ----
  cancelarBtn.addEventListener("click", () => {
    modalCurso.classList.remove("ativo");
  });

  // ---- Clique nos botões da tabela ----
  tabelaCursos.addEventListener("click", e => {
    const btn = e.target;
    if (btn.tagName !== "BUTTON") return;

    const id = btn.dataset.id;
    const acao = btn.dataset.acao;

    if (acao === "editar") {
      editarCurso(id, btn.dataset.titulo, btn.dataset.descricao, btn.dataset.imagem);
    } else if (acao === "alunos") {
      verAlunos(id);
    }
  });

  // ---- Editar curso ----
  function editarCurso(id, titulo, descricao, imagem) {
    document.getElementById("cursoId").value = id;
    document.getElementById("titulo").value = titulo;
    document.getElementById("descricao").value = descricao;
    document.getElementById("caminhoImagem").value = imagem || "";
    modalTitulo.textContent = "Editar Curso";
    modalCurso.classList.add("ativo");
  }

// ---- Ver alunos ----
function verAlunos(cursoId) {
  const modalAlunos = document.getElementById("modalAlunos");
  const listaAlunos = document.getElementById("listaAlunos");
  const fecharAlunosBtn = document.getElementById("fecharAlunosBtn");

  // Limpa a lista anterior
  listaAlunos.innerHTML = "<p>Carregando alunos...</p>";
  modalAlunos.classList.add("ativo");

  fetch(`http://localhost:8080/cursos/${cursoId}/alunos`)
    .then(resp => {
      if (!resp.ok) throw new Error("Erro ao buscar alunos");
      return resp.json();
    })
    .then(alunos => {
      if (!alunos.length) {
        listaAlunos.innerHTML = "<p>Nenhum aluno matriculado neste curso.</p>";
        return;
      }

      listaAlunos.innerHTML = alunos
        .map(a => `<p><span>${a.nome}</span> — ${a.email}</p>`)
        .join("");
    })
    .catch(() => {
      listaAlunos.innerHTML = "<p>Erro ao carregar alunos.</p>";
    });

  fecharAlunosBtn.onclick = () => {
    modalAlunos.classList.remove("ativo");
  };
}



  // ---- Carregar cursos ao abrir ----
  carregarCursos();
});

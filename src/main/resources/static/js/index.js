document.addEventListener("DOMContentLoaded", () => {
  const grade = document.getElementById("lista-cursos");
  const botaoEntrar = document.getElementById("botaoEntrar");
  const usuarioInfo = document.getElementById("usuarioInfo");
  const nomeUsuario = document.getElementById("nomeUsuario");
  const botaoSair = document.getElementById("botaoSair");

  const linkPortalAluno = document.querySelector('a[href="../html/portal-aluno.html"]');
  const linkPortalProfessor = document.querySelector('a[href="../html/portal-professor.html"]');

  function atualizarCabecalho() {
    const usuarioLogado = JSON.parse(localStorage.getItem("usuarioLogado"));

    if (usuarioLogado) {
      botaoEntrar.style.display = "none";
      usuarioInfo.style.display = "flex";
      nomeUsuario.textContent = `Olá, ${usuarioLogado.nome}!`;

      if (usuarioLogado.perfil === "ALUNO") {
        linkPortalAluno.style.display = "inline-block";
        linkPortalProfessor.style.display = "none";
      } else if (usuarioLogado.perfil === "PROFESSOR") {
        linkPortalProfessor.style.display = "inline-block";
        linkPortalAluno.style.display = "none";
      } else {
        linkPortalAluno.style.display = "none";
        linkPortalProfessor.style.display = "none";
      }
    } else {
      botaoEntrar.style.display = "inline-block";
      usuarioInfo.style.display = "none";
      linkPortalAluno.style.display = "none";
      linkPortalProfessor.style.display = "none";
    }
  }

  atualizarCabecalho();

  botaoEntrar.addEventListener("click", () => {
    window.location.href = "login.html";
  });

  botaoSair.addEventListener("click", () => {
    localStorage.removeItem("usuarioLogado");
    atualizarCabecalho();
    window.location.reload();
  });

  if (grade) grade.innerHTML = "<p>Carregando cursos...</p>";

  const usuarioLogado = JSON.parse(localStorage.getItem("usuarioLogado"));
  let cursosMatriculados = [];
  const promessas = [];

  // 🔹 Buscar cursos em que o aluno já está matriculado
  if (usuarioLogado && usuarioLogado.perfil === "ALUNO") {
    promessas.push(
      fetch(`http://localhost:8080/matriculas/aluno/${usuarioLogado.id}`)
        .then(resp => resp.ok ? resp.json() : [])
        .then(matriculas => {
          cursosMatriculados = matriculas.map(m => m.idCurso);
        })
        .catch(() => {
          cursosMatriculados = [];
        })
    );
  }

  // 🔹 Depois carregar os cursos
  Promise.all(promessas).then(() => {
    fetch("http://localhost:8080/cursos/")
      .then(resp => {
        if (!resp.ok) throw new Error("Erro ao carregar cursos");
        return resp.json();
      })
      .then(cursos => {
        if (!cursos || cursos.length === 0) {
          grade.innerHTML = "<p>Nenhum curso disponível no momento.</p>";
          return;
        }

        grade.innerHTML = "";

        cursos.forEach(curso => {
          const card = document.createElement("article");
          card.classList.add("cartao");

          const imageSrc = curso.caminhoImagem && (curso.caminhoImagem.startsWith('http://') || curso.caminhoImagem.startsWith('https://'))
            ? curso.caminhoImagem
            : `http://localhost:8080${curso.caminhoImagem || ''}`;

          card.innerHTML = `
            <img src="${imageSrc}" alt="${curso.titulo}" class="cartao-imagem">
            <div class="cartao-corpo">
              <h3 class="cartao-titulo">${curso.titulo}</h3>
              <p class="cartao-descricao">${curso.descricao}</p>
              <p><strong>Professor:</strong> ${curso.professor || "Desconhecido"}</p>
            </div>
          `;

          // 🔹 Controle de botões e status
          if (usuarioLogado && usuarioLogado.perfil === "PROFESSOR") {
            // Professores não têm botão
          } else if (usuarioLogado && usuarioLogado.perfil === "ALUNO") {
            if (cursosMatriculados.includes(curso.id)) {
              const status = document.createElement("button");
              status.classList.add("matriculado");
              status.textContent = "Matriculado";
              status.disabled = true;
              card.querySelector(".cartao-corpo").appendChild(status);
            } else {
              const btnInscrever = document.createElement("button");
              btnInscrever.classList.add("botao", "botao-principal", "botao-inscrever");
              btnInscrever.textContent = "Matricule-se";

              btnInscrever.addEventListener("click", () => {
                fetch(`http://localhost:8080/matriculas/adicionar`, {
                  method: "POST",
                  headers: { "Content-Type": "application/json" },
                  body: JSON.stringify({
                    alunoId: usuarioLogado.id,
                    cursoId: curso.id
                  }),
                })
                  .then(response => {
                    if (!response.ok) throw new Error("Erro ao realizar inscrição");
                    return response.json();
                  })
                  .then(() => {
                    alert(`Inscrição realizada com sucesso no curso "${curso.titulo}"!`);
                    window.location.reload();
                  })
                  .catch(() => {
                    alert("Erro ao realizar inscrição. Tente novamente mais tarde.");
                  });
              });

              card.querySelector(".cartao-corpo").appendChild(btnInscrever);
            }
          } else {
            const btnInscrever = document.createElement("button");
            btnInscrever.classList.add("botao", "botao-principal", "botao-inscrever");
            btnInscrever.textContent = "Matricule-se";
            btnInscrever.addEventListener("click", () => {
              alert("Você precisa estar logado para se matricular em um curso.");
              window.location.href = "login.html";
            });
            card.querySelector(".cartao-corpo").appendChild(btnInscrever);
          }

          grade.appendChild(card);
        });
      })
      .catch(() => {
        grade.innerHTML = "<p>Erro ao carregar os cursos. Tente novamente mais tarde.</p>";
      });
  });
});

document.addEventListener("DOMContentLoaded", () => {
  const grade = document.getElementById("lista-cursos");

  const botaoEntrar = document.getElementById("botaoEntrar");
  const usuarioInfo = document.getElementById("usuarioInfo");
  const nomeUsuario = document.getElementById("nomeUsuario");
  const botaoSair = document.getElementById("botaoSair");

  const linkPortalAluno = document.querySelector('a[href="../html/portal-aluno.html"]');
  const linkPortalProfessor = document.querySelector('a[href="../html/portal-professor.html"]');

  // Atualiza o cabeçalho com base no usuário logado
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

  // Eventos do cabeçalho
  botaoEntrar.addEventListener("click", () => {
    window.location.href = "login.html";
  });

  botaoSair.addEventListener("click", () => {
    localStorage.removeItem("usuarioLogado");
    atualizarCabecalho();
    window.location.reload(); 
  });

  // Mostra mensagem de carregamento
  if(grade)
    grade.innerHTML = "<p>Carregando cursos...</p>";

  // Busca cursos do backend
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
      const usuarioLogado = JSON.parse(localStorage.getItem("usuarioLogado"));

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
            <button class="botao botao-principal botao-inscrever">Inscrever-se</button>
          </div>
        `;

        const btnInscrever = card.querySelector(".botao-inscrever");

        // Esconde botão para professores
        if (usuarioLogado && usuarioLogado.perfil === "PROFESSOR") {
          btnInscrever.style.display = "none";
        } else {
          // Só alunos podem se inscrever
          btnInscrever.addEventListener("click", () => {
            if (!usuarioLogado) {
              alert("Você precisa estar logado para se inscrever em um curso.");
              window.location.href = "login.html";
              return;
            }

            const alunoId = usuarioLogado.id;
            const cursoId = curso.id;

            fetch(`http://localhost:8080/matriculas/adicionar`, {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify({
                alunoId: alunoId,
                cursoId: cursoId
              }),
            })
              .then(response => {
                if (!response.ok) throw new Error("Erro ao realizar inscrição");
                return response.json();
              })
              .then(() => {
                alert(`Inscrição realizada com sucesso no curso "${curso.titulo}"!`);
              })
              .catch(() => {
                alert("Erro ao realizar inscrição. Tente novamente mais tarde.");
              });
          });
        }

        grade.appendChild(card);
      });
    })
    .catch(() => {
      if(grade)
        grade.innerHTML = "<p>Erro ao carregar os cursos. Tente novamente mais tarde.</p>";
    });
});

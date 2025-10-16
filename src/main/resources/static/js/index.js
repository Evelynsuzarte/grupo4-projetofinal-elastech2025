document.addEventListener("DOMContentLoaded", () => {
  const grade = document.getElementById("lista-cursos");

  const botaoEntrar = document.getElementById("botaoEntrar");
  const usuarioInfo = document.getElementById("usuarioInfo");
  const nomeUsuario = document.getElementById("nomeUsuario");
  const botaoSair = document.getElementById("botaoSair");

  function atualizarCabecalho() {
    const usuarioLogado = JSON.parse(localStorage.getItem("usuarioLogado"));

    if (usuarioLogado) {
      botaoEntrar.style.display = "none";
      usuarioInfo.style.display = "flex";
      nomeUsuario.textContent = `Olá, ${usuarioLogado.nome}!`;
    } else {
      botaoEntrar.style.display = "inline-block";
      usuarioInfo.style.display = "none";
    }
  }

  atualizarCabecalho();

  botaoEntrar.addEventListener("click", () => {
    window.location.href = "login.html";
  });

  botaoSair.addEventListener("click", () => {
    localStorage.removeItem("usuarioLogado");
    atualizarCabecalho();
  });

  grade.innerHTML = "<p>Carregando cursos...</p>";

  fetch("http://localhost:8080/cursos")
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

        card.innerHTML = `
          <img src="http://localhost:8080${curso.caminhoImagem}" 
               alt="${curso.titulo}" class="cartao-imagem">
          <div class="cartao-corpo">
            <h3 class="cartao-titulo">${curso.titulo}</h3>
            <p class="cartao-descricao">${curso.descricao}</p>
            <p><strong>Professor:</strong> ${curso.professor?.nome || "Desconhecido"}</p>
            <button class="botao botao-principal botao-inscrever">Inscrever-se</button>
          </div>
        `;

        const btnInscrever = card.querySelector(".botao-inscrever");
        btnInscrever.addEventListener("click", () => {
          const usuarioLogado = JSON.parse(localStorage.getItem("usuarioLogado"));

          if (!usuarioLogado) {
            alert("Você precisa estar logado para se inscrever em um curso.");
            window.location.href = "login.html";
            return;
          }

          const alunoId = usuarioLogado.id;
          const cursoId = curso.id;

          fetch(`http://localhost:8080/matriculas?alunoId=${alunoId}&cursoId=${cursoId}`, {
            method: "POST",
          })
            .then(response => {
              if (!response.ok) throw new Error("Erro ao realizar inscrição");
              return response.json();
            })
            .then(() => {
              alert(`Inscrição realizada com sucesso no curso "${curso.titulo}"!`);
            })
            .catch(error => {
              alert("Erro ao realizar inscrição. Tente novamente mais tarde.");
            });
        });

        grade.appendChild(card);
      });
    })
    .catch(error => {
      grade.innerHTML = "<p>Erro ao carregar os cursos. Tente novamente mais tarde.</p>";
    });
});

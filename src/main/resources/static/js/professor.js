document.addEventListener("DOMContentLoaded", () => {
  const lista = document.getElementById("lista-cursos");
  const usuario = JSON.parse(localStorage.getItem("usuarioLogado"));
  const botaoSair = document.getElementById("botaoSair");

  if (!usuario || usuario.perfil !== "PROFESSOR") {
    alert("Apenas professores podem acessar este portal.");
    window.location.href = "login.html";
    return;
  }

  botaoSair.addEventListener("click", () => {
    localStorage.removeItem("usuarioLogado");
    window.location.href = "login.html";
  });

  const professorId = usuario.id;

  fetch(`http://localhost:8080/cursos/professor/${professorId}`)
    .then(resp => {
      if (!resp.ok) throw new Error("Erro ao buscar cursos");
      return resp.json();
    })
    .then(cursos => {
      lista.innerHTML = "";

      if (!cursos.length) {
        lista.innerHTML = "<p>Nenhum curso cadastrado ainda.</p>";
        return;
      }

      cursos.forEach(curso => {
        const card = document.createElement("article");
        card.classList.add("cartao");
        card.innerHTML = `
          <img src="${curso.caminhoImagem ? "http://localhost:8080" + curso.caminhoImagem : "../imagens/sem-imagem.jpg"}" class="cartao-imagem" alt="Imagem do curso">
          <div class="cartao-corpo">
            <h3 class="cartao-titulo">${curso.titulo}</h3>
            <p class="cartao-descricao">${curso.descricao}</p>
            <div class="cartao-acoes">
              <button class="botao botao-borda" onclick="verMatriculas(${curso.id})">Ver Alunos</button>
              <button class="botao botao-principal" onclick="editarCurso(${curso.id})">Editar</button>
            </div>
          </div>
        `;
        lista.appendChild(card);
      });
    })
    .catch(err => {
      lista.innerHTML = "<p>Erro ao carregar cursos.</p>";
    });
});

function verMatriculas(cursoId) {
  window.location.href = `matriculas-curso.html?cursoId=${cursoId}`;
}

function editarCurso(id) {
  window.location.href = `editar-curso.html?id=${id}`;
}

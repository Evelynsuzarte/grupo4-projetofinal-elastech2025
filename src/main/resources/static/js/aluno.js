document.addEventListener("DOMContentLoaded", () => {
  const tabela = document.getElementById("tabela-matriculas");
  const usuarioLogado = localStorage.getItem("usuarioLogado");

  if (!usuarioLogado) {
    setTimeout(() => {
      alert("Você precisa estar logado para acessar o portal do aluno.");
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

  fetch(`http://localhost:8080/matriculas/${usuario.id}`)
    .then(resp => {
      if (!resp.ok) throw new Error("Erro ao buscar matrículas");
      return resp.json();
    })
    .then(matriculas => {
      tabela.innerHTML = "";

      if (!matriculas.length) {
        tabela.innerHTML = "<tr><td colspan='4'>Nenhuma matrícula encontrada.</td></tr>";
        return;
      }

      matriculas.forEach(m => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${m.curso?.titulo || "Sem título"}</td>
          <td>${m.curso?.professor?.nome || "N/A"}</td>
          <td>${new Date(m.dataMatricula).toLocaleDateString("pt-BR")}</td>
        `;
        tabela.appendChild(tr);
      });
    })
    .catch(() => {
      tabela.innerHTML = "<tr><td colspan='4'>Erro ao carregar matrículas.</td></tr>";
    });
});

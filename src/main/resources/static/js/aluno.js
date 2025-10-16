document.addEventListener("DOMContentLoaded", () => {
  const tabela = document.getElementById("tabela-matriculas");
  const usuario = JSON.parse(localStorage.getItem("usuarioLogado"));

  if (!usuario) {
    alert("Você precisa estar logado para acessar o portal do aluno.");
    window.location.href = "login.html";
    return;
  }

  const alunoId = usuario.id;

  fetch(`http://localhost:8080/matriculas/aluno/${alunoId}`)
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
          <td>${m.status || "Ativo"}</td>
          <td>
            <button class="botao botao-borda" onclick="cancelarMatricula(${m.id})">Cancelar</button>
          </td>
        `;
        tabela.appendChild(tr);
      });
    })
    .catch(err => {
      tabela.innerHTML = "<tr><td colspan='4'>Erro ao carregar matrículas.</td></tr>";
    });
});

function cancelarMatricula(id) {
  if (!confirm("Tem certeza que deseja cancelar esta matrícula?")) return;

  fetch(`http://localhost:8080/matriculas/${id}`, { method: "DELETE" })
    .then(resp => {
      if (!resp.ok) throw new Error("Erro ao cancelar matrícula");
      alert("Matrícula cancelada com sucesso!");
      location.reload();
    })
    .catch(err => alert("Erro ao cancelar matrícula: " + err.message));
}

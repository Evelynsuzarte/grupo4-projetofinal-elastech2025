document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("formLogin");

  form.addEventListener("submit", event => {
    event.preventDefault();

    const email = document.getElementById("email").value.trim();
    const senha = document.getElementById("senha").value.trim();

    if (!email || !senha) {
      alert("Preencha todos os campos!");
      return;
    }

    fetch("http://localhost:8080/usuarios/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, senha }),
    })
      .then(response => {
        if (!response.ok) throw new Error("Credenciais inválidas");
        return response.json();
      })
      .then(usuario => {
        localStorage.setItem("usuarioLogado", JSON.stringify(usuario));
        alert(`Bem-vindo(a), ${usuario.nome}!`);

        // Redireciona conforme o perfil
        if (usuario.perfil === "ALUNO") {
          window.location.href = "portal-aluno.html";
        } else if (usuario.perfil === "PROFESSOR") {
          window.location.href = "portal-professor.html";
        } else {
          window.location.href = "index.html";
        }
      })
      .catch(err => {
        alert("Erro no login. Verifique suas credenciais.");
      });
  });
});

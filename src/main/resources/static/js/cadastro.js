document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("formCadastro");

  form.addEventListener("submit", event => {
    event.preventDefault();

    const nome = document.getElementById("nome").value.trim();
    const email = document.getElementById("email").value.trim();
    const senha = document.getElementById("senha").value.trim();
    const perfil = document.getElementById("perfil").value;

    if (!nome || !email || !senha || !perfil) {
      alert("Preencha todos os campos!");
      return;
    }

    fetch("http://localhost:8080/usuarios", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nome, email, senha, perfil }),
    })
      .then(response => {
        if (!response.ok) throw new Error("Erro ao cadastrar usuário");
        return response.json();
      })
      .then(() => {
        alert("Cadastro realizado com sucesso! Faça login para continuar.");
        window.location.href = "login.html";
      })
      .catch(err => {
        alert("Erro ao cadastrar. Verifique os dados e tente novamente.");
      });
  });
});

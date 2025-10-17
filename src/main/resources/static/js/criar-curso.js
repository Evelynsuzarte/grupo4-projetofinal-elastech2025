document.getElementById("form-curso").addEventListener("submit", async (e) => {
  e.preventDefault();

  const usuario = JSON.parse(localStorage.getItem("usuarioLogado"));
  if (!usuario || usuario.tipoUsuario !== "PROFESSOR") {
    alert("Apenas professores podem criar cursos.");
    return;
  }

  const curso = {
    titulo: document.getElementById("titulo").value,
    descricao: document.getElementById("descricao").value,
    caminhoImagem: document.getElementById("caminhoImagem").value,
    professor: { id: usuario.id }
  };

  try {
    const resp = await fetch("http://localhost:8080/cursos/adicionar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(curso)
    });

    if (!resp.ok) throw new Error("Erro ao criar curso");

    alert("Curso criado com sucesso!");
    window.location.href = "portal-professor.html";
  } catch (err) {
    alert(err.message);
  }
});

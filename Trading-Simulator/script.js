document.getElementById("year").textContent = new Date().getFullYear();

const form = document.getElementById("contactForm");
const status = document.getElementById("status");

form.addEventListener("submit", (e) => {
  e.preventDefault();
  status.textContent = "Thanks! (This demo form doesn’t send emails yet.)";
  form.reset();
});

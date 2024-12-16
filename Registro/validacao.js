document.getElementById("formulario").addEventListener("submit", function (event) {
    event.preventDefault();

    const dateInput = document.getElementById("Idade").value;
    const errorMessage = document.getElementById("errorMessage");

    const datePattern = /^\d{2}\/\d{2}\/\d{4}$/;
    if (!datePattern.test(dateInput)) {
    errorMessage.textContent = "Formato inválido! Use o formato dd/mm/aaaa.";
    errorMessage.style.display = "block";
    return;
    }


    const [day, month, year] = dateInput.split("/").map(Number);

    const isValidDate = (d, m, y) => {
    const currentYear = new Date().getFullYear();
    const date = new Date(y, m - 1, d);
    date.getFullYear() >= currentYear ? errorMessage.textContent = "Data inválida! Por favor, insira uma data real." : (
        date.getFullYear() === y &&
        date.getMonth() === m - 1 &&
        date.getDate() === d
    );
    };
    

    if (!isValidDate(day, month, year)) {
    errorMessage.textContent = "Data inválida! Por favor, insira uma data real.";
    errorMessage.style.display = "block";
    return;
    }

    errorMessage.style.display = "none";
    alert("Data válida: " + dateInput);
});
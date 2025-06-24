function query(event) {
    event.preventDefault();
    const statusCondition = document.querySelector('select#level').value;
    const a = event.currentTarget;
    a.href = a.href +"&status=" + statusCondition;
    window.location.href = a.href;
}
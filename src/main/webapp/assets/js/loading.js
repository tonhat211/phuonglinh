function isLoading(con) {
    const loading = document.querySelector('#loading');
    if(con) {
        console.log("isLoading true");
        loading.classList.add('active');
    } else {
        console.log("isLoading false");
        if(loading.classList.contains('active')) {
            loading.classList.remove('active');
        }
    }
}


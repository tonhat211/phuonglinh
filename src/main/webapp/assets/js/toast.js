function toast({title = '', message = '', type = 'info', duration = 3000, container='toast-header'}) {
    const main = document.querySelector(container);
    if (main) {
        const toast = document.createElement('div');
        // auto remove toast
        const autoRemoveId = setTimeout(function() {
            main.removeChild(toast);
        }, duration + 1000);

        // remove toast by click
        toast.onclick = function(e) {
            if (e.target.closest('.toast__close')) {
                main.removeChild(toast);
                clearTimeout(autoRemoveId);
            }
        }

        const icons = {
            success: 'fa-solid fa-circle-check',
            info: 'fa-solid fa-circle-info',
            warning: 'fa-solid fa-triangle-exclamation',
            error: 'fa-solid fa-xmark',

        };
        const icon = icons[type];
        toast.classList.add('toastt', `toast--${type}`);
        const delay = (duration / 1000).toFixed(2);
        toast.style.animation = `slideInLeft ease 0.3s, fadeOut linear 1s ${delay}s forwards`;
        toast.innerHTML = `
                    <div class="toast__icon">
                        <i class="${icon}"></i>

                    </div>
                    <div class="toast__body">
                        <h3 class="toast__title">${title}</h3>
                        <p class="toast__msg">${message} </p>
                    </div>
                    <div class="toast__close">
                        <i class="fa-solid fa-xmark"></i>
                    </div>
                `;
        main.appendChild(toast);

    }
}


function showSuccessToast(messagein,container = '#toast') {
    toast({
        title: 'Successful',
        message: messagein,
        type: 'success',
        duration: 1200,
        container: container
    })
}

function showErrorToast() {
    toast({
        title: 'Error',
        message: 'errorrrrrrrr, code cuar banj bij loiox',
        type: 'error',
        duration: 4000
    })
}

//toast 2
function toast2({title = '', message = '', type = 'info', duration = 3000}) {
    const main = document.getElementById('toast-2');

    if (main) {
        if(main.firstChild) {
            main.removeChild(main.firstChild);
        }
        const toast = document.createElement('div');

        // auto remove toast

        const autoRemoveId = setTimeout(function() {
            main.removeChild(toast);
        }, duration + 1000);
        toast.classList.add('toastt-2', `toast-2--${type}`);
        const delay = (duration / 1000).toFixed(2);
        toast.style.animation = `fadeIn ease 0.3s, fadeOut linear 1s ${delay}s forwards`;
        toast.innerHTML = `
                <p class="toast__msg"  style="color: white">${message} </p>
                `;

        main.appendChild(toast);

    }
}


function showSuccessToast2(messagein,sound="sound") {
    if(sound=="sound") {
        playSound('assets/sound/successful.mp3');
    }
    toast2({
        title: 'Successful',
        message: messagein,
        type: 'success',
        duration: 700
    })
}

function playSound(url) {
    const audio = new Audio(url);
    audio.play();
}

function showErrorToast2(messagein,sound="sound") {
    if(sound=="sound") {
        playSound('assets/sound/error.mp3');
    }
    toast2({
        title: 'error',
        message: messagein,
        type: 'error',
        duration: 700
    })
}

function showInfoToast2(messagein,sound="sound") {
    if(sound=="sound") {
        playSound('assets/sound/error.mp3');
    }
    toast2({
        title: 'info',
        message: messagein,
        type: 'info',
        duration: 700
    })
}
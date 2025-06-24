function radioElements(element) {
    const elements = document.querySelectorAll(element);
    elements.forEach(function(e) {
        e.addEventListener('click', function() {
            const parent = this.closest('.group');
            if(!parent) return;
            const subs = parent.querySelectorAll(element);
            subs.forEach(function(item) {
                item.classList.remove('active');
            });
            this.classList.add('active');
        });
    });
}

function changeToProductUrl() {
    const basePath = window.location.pathname.split("/").slice(0, 2).join("/");

    const newPath = '/product'; // Phần path mới bạn muốn thay đổi
    window.history.pushState(null, '', basePath + newPath);

}

function createEtc() {
    const iconElement = document.createElement('i');
    iconElement.classList.add('bi', 'bi-three-dots');
    return iconElement;
}


function hideModal(modalID) {
    document.querySelector(modalID).classList.remove('active');
}

function closeModal(event) {
    event.currentTarget.closest(".modall").classList.remove("active");
}

function setUpPagination(id) {
    const pagination = document.querySelector(id);
    const pageLinks = pagination.querySelectorAll('.page-item .page-link');
    var len = pageLinks.length;
    const etcs = pagination.querySelectorAll('.etc');
    pageLinks[0].classList.add('active');
    if (len > 4) {
        etcs.forEach(etc => {
            etc.style.display = 'block';
        });
        pageLinks.forEach(page => {
            page.style.display = 'none';
            page.addEventListener('click', function (event) {
                updatePagination('#order-pagination', event.currentTarget.getAttribute('data-value') - 1);
            });
        });
        pageLinks[0].style.display = 'block';
        pageLinks[1].style.display = 'block';
        pageLinks[2].style.display = 'block';
        pageLinks[3].style.display = 'block';
        pageLinks[len - 1].style.display = 'block';
    } else {
        etcs.forEach(etc => {
            etc.style.display = 'none';
        });
    }
}

function updatePagination(id, indexActive) {
    const pagination = document.querySelector(id);
    const pageLinks = pagination.querySelectorAll('.page-item .page-link');
    pageLinks.forEach(pageLink => {
        if(pageLink.classList.contains('active'))
            pageLink.classList.remove('active');
    });
    var len = pageLinks.length;
    if (len > 4) {
        pageLinks.forEach(page => {
            page.style.display = 'none';
        });
        pageLinks[0].style.display = 'block';
        pageLinks[len - 1].style.display = 'block';

        // var indexActive = event.currentTarget.getAttribute('data-value') - 1;
        if (indexActive <= 2) {
            pageLinks[1].style.display = 'block';
            pageLinks[2].style.display = 'block';
        } else if (indexActive >= len-1) {
            pageLinks[len - 2].style.display = 'block';
            pageLinks[len - 3].style.display = 'block';
        } else {
            pageLinks[indexActive - 2].style.display = 'block';
            pageLinks[indexActive -1].style.display = 'block';
            pageLinks[indexActive].style.display = 'block';
        }
    }
    pagination.querySelector(`.page-item .page-link[data-value="${indexActive}"]`).classList.add('active');

}

function nextPage(event) {
    event.preventDefault();
    const pagination = event.currentTarget.closest('.group');
    const pageLinks = pagination.querySelectorAll('.page-item .page-link');
    var indexActive = pagination.querySelector('.page-item .page-link.active').getAttribute('data-value');
    if (indexActive < pageLinks.length) {
        pageLinks[indexActive].click();
    } else {
        showInfoToast2("Đã tới cuối danh sách!","none");
    }
}

function previousPage(event) {
    event.preventDefault();
    const pagination = event.currentTarget.closest('.group');
    const pageLinks = pagination.querySelectorAll('.page-item .page-link');
    var indexActive = pagination.querySelector('.page-item .page-link.active').getAttribute('data-value') - 1;
    if (indexActive > 0) {
        pageLinks[indexActive - 1].click();
    } else {
        showInfoToast2("Đã tới đầu danh sách!","none");
    }
}

function setDefaultOption(name) {
    document.querySelectorAll('select[name="'+name+'"]').forEach(select => {
        const defaultValue = select.getAttribute('data-default');
        select.value = defaultValue;
    });
}

function setSelectedOption(id,value) {
    document.querySelector('select#'+id).value = value;
}

function openModal(modalID) {
    document.querySelector(modalID).classList.add('active');
}


function addClassLocked(element) {
    document.querySelector(element).classList.add('locked');
}
function removeClassLocked(element) {
    document.querySelector(element).classList.remove('locked');
}
function hide(element) {
    document.querySelector(element).style.display="none";
}

function setInfosStatus(group,status) {
    const inputs = group.querySelectorAll("input");
    const selects = group.querySelectorAll("select");
    var status =!status;
    inputs.forEach((input) => {
        input.readOnly = status;
    });
    selects.forEach((select) => {
        select.disabled = status;
    });
    if( group.querySelector("select[name='action']"))
        group.querySelector("select[name='action']").disabled =false;
}

function scrollToBottom() {
    window.scrollTo({
        top: document.body.scrollHeight, // Độ cao của toàn bộ nội dung trang
        behavior: "smooth" // Cuộn mượt mà
    });
}
function refresh() {
    location.reload();
}

function setSearchValue(query,value) {
    document.querySelector(query).value = value;
}


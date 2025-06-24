function showMoreOrderDetail(event,element) {
    const parent = event.currentTarget.closest(element);
    parent.querySelector('.other-detail').classList.remove('hide');
    parent.querySelector('.btn-more-container').innerHTML = '<p class="btn-more" onclick="hideMoreOrderDetail(event,\'.order-item\');">Thu gọn <i class="bi bi-chevron-up"></i></p>';

}

function hideMoreOrderDetail(event,element) {
    const parent = event.currentTarget.closest(element);
    parent.querySelector('.other-detail').classList.add('hide');
    parent.querySelector('.btn-more-container').innerHTML = '<p class="btn-more" onclick="showMoreOrderDetail(event,\'.order-item\');">Xem thêm <i class="bi bi-chevron-down"></i></p>';
}

function renderBlankComments(blankComments) {
    console.log("blank comments size: " + blankComments.length);
    const blankCommentListContainer = $('.feedback-list');
    blankCommentListContainer.empty();

    if(blankComments.length === 0){
        showInfoToast2("Đã đánh giá rồi!",'none');
        const feedbackField = document.querySelector('.feedback-field');
        feedbackField.classList.add('hide_tab');
        if(feedbackField.classList.contains('feedback-field_tab')) {
            feedbackField.classList.remove('feedback-field_tab');
        }
        feedbackField.classList.add('hide_mob');
        if(feedbackField.classList.contains('feedback-field_mob')) {
            feedbackField.classList.remove('feedback-field_mob');
        }
    }

    blankComments.forEach(function(blankComment) {
        const addressItem = `
            <div class="feedback-item group">
                <input type="text" name="orderID" value="${blankComment.prominence}" hidden>
                <input type="text" name="productID" value="${blankComment.id}" hidden>
                <div class="flex-roww" style="padding: 6px;">
                    <div class="grid-col-1_5 ">
                        <img class="img-product" src="./assets/img/thumbnail/${blankComment.thumbnail}" alt="" style="width: 100%;">
                    </div>
                    <div class="product-info">
                        <p class="name">${blankComment.name}</p>
                        <p class="classifies">${blankComment.description}</p>
                    </div>
                </div>
                <div class="rating-main flex-roww" id="rating-presentation-${blankComment.id}">
                    <div class="rating">
                        <span class="star" data-value="1"><i class="bi bi-star"></i></span>
                        <span class="star" data-value="2"><i class="bi bi-star"></i></span>
                        <span class="star" data-value="3"><i class="bi bi-star"></i></span>
                        <span class="star" data-value="4"><i class="bi bi-star"></i></span>
                        <span class="star" data-value="5"><i class="bi bi-star"></i></span>
                    </div>
                    <span class="form-error" style="margin-left: 10px"></span>
                    <input type="text" name="rating-index" value="0" hidden>
                </div>
                <textarea contenteditable="true" class="editor" name="content" placeholder="Bình luận..." maxlength="150"></textarea>
                <div class="flex-roww" style="justify-content: end">
                    <button class="btn btn-primary" type="button" onclick="feedBack(event)">Gửi</button>
                </div>
                <script>
                    activeRating('#rating-presentation-${blankComment.id}');
                </script>
                 <div style="margin: 10px 0" class="seperate-horizontal-100"></div>
            </div>
                `;

        // Thêm địa chỉ vào container
        blankCommentListContainer.append(addressItem);
    });
}

function feedBack(event) {
    const group = event.currentTarget.closest('.group');
    var orderID = group.querySelector('input[name="orderID"]').value;
    var productID = group.querySelector('input[name="productID"]').value;
    var classifies = group.querySelector('.classifies').innerText;
    var ratingIndex = group.querySelector('input[name="rating-index"]').value;
    var content = group.querySelector('textarea[name="content"]').value;
    if(ratingIndex==0) group.querySelector('.form-error').innerText = "Vui lòng thêm đánh giá";
    else {
        $.ajax({
            url: 'order', // URL của Servlet hoặc API
            type: 'POST',
            data: {a: "comment", productID:productID, classifies:classifies,ratingIndex:ratingIndex,content:content, orderID:orderID },
            success: function(data) {
                $('#cusOrder-response').html(data);
                group.style.display = 'none';
            },
            error: function(error) {

            }
        });
    }
}

function getBlankComments(id) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'order?a=rcomment&id='+id, // URL của Servlet hoặc API
            type: 'POST',
            dataType: 'json',
            success: function(data) {
                console.log(data);
                resolve(data); // Gọi resolve với dữ liệu trả về
            },
            error: function(error) {
                reject(error); // Nếu có lỗi, gọi reject
            }
        });
    });
}

function rComment(event,id) {
    event.preventDefault();
    event.stopPropagation();
    getBlankComments(id).then(function(blankComments) {
        renderBlankComments(blankComments); // Gọi render khi dữ liệu đã được lấy về
    }).catch(function(error) {
        $('.feedback-list').html('<p>Lỗi, chưa thể bình luận.</p>'); // Hiển thị thông báo lỗi nếu có
    });
    const rect = event.currentTarget.closest('.order-item').getBoundingClientRect();
    const feedbackContainer = document.querySelector('.feedback-container');
    const feedbackField = document.querySelector('.feedback-field');
    var differentDistance = feedbackField.getBoundingClientRect().top;
    feedbackContainer.style.top = `${rect.top - differentDistance}px`; // Cùng hàng với thẻ hiện tại
    feedbackField.classList.add('feedback-field_tab');
    if(feedbackField.classList.contains('hide_tab')) {
        feedbackField.classList.remove('hide_tab');
    }
    feedbackField.classList.add('feedback-field_mob');
    if(feedbackField.classList.contains('hide_mob')) {
        feedbackField.classList.remove('hide_mob');
    }


}

function scrollToActive() {
    const activeElement = document.querySelector('.tab-menu .active'); // Thẻ được chọn
    if (activeElement) {
        activeElement.scrollIntoView({
            behavior: 'smooth', // Cuộn mượt mà
            block: 'nearest',   // Vùng gần nhất
            inline: 'center'    // Canh giữa theo chiều ngang
        });
    }
}


// dam bao dom da tai xong
document.addEventListener("DOMContentLoaded", function() {
    scrollToActive();

    document.addEventListener('click', function(event) {
        const feedbackField = document.querySelector('.feedback-field');
        const feedbackContainer = document.querySelector('.feedback-container');
        console.log(feedbackContainer);
        if (!feedbackContainer.contains(event.target) && !event.target.classList.contains('feedback-container')) {
            if (feedbackField.classList.contains('feedback-field_tab')) {
                feedbackField.classList.remove('feedback-field_tab');
            }
            feedbackField.classList.add('hide_tab');
            if (feedbackField.classList.contains('feedback-field_mob')) {
                feedbackField.classList.remove('feedback-field_mob');
            }
            feedbackField.classList.add('hide_mob');
        }
    });


    document.querySelector('.feedback-container').addEventListener('click', function(event) {
        event.stopPropagation();
    });

});

function confirmCancel(event,id) {
    event.preventDefault();
    var modalID = '#cancel-confirm-modal';
    openModal2(modalID);
    document.querySelector(modalID).querySelector('.object').innerText = id;
    document.querySelector(modalID).querySelector('input[name="id"]').value = id;
}

function confirmComplete(event,id) {
    event.preventDefault();
    var modalID = '#complete-confirm-modal';
    openModal2(modalID);
    document.querySelector(modalID).querySelector('.object').innerText = id;
    document.querySelector(modalID).querySelector('input[name="id"]').value = id;
}

function setupConfirm(id,modalID) {
    openModal(modalID)
    document.querySelector(modalID).querySelector('.id').innerText = id;
    document.querySelector(modalID).querySelector('input[name="id"]').value = id;
}

function closeModal(event) {
    event.currentTarget.closest(".modall").classList.remove("active");
}

function openModal2(modalID) {
    document.querySelector(modalID).classList.add('active');
}


function cancel(event) {
    event.preventDefault();
    const group = event.currentTarget.closest('.group');
    var id = group.querySelector('input[name="id"]').value;
    var url = event.currentTarget.href + "&id="+id;
    window.location.href = url;
}

function showAddress(event) {
    event.preventDefault();
    const group = event.currentTarget.closest('.group');
    $.ajax({
        type: "post",
        url: event.currentTarget.href,
        success: function(data) {
            group.querySelector('.address-presentation').innerText = data;
        },
        error: function(error) {
            console.error("Error during querying info: ", error);
        }
    });

}

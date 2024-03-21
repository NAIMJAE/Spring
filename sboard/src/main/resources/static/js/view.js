document.addEventListener('click', async function (e){
    const btnValue = e.target.textContent;
    console.log(btnValue);

    // 삭제
    if (btnValue === "삭제"){
        const no = e.target.dataset.no;
        const li = e.target.parentElement;

        const result = confirm("정말 삭제하시겠습니까?");
        if(result){
            await fetch(`/sboard/article/commentDelete/${no}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('댓글 삭제에 실패했습니다.');
                    }
                    return response.json()
                })
                .then(data => {
                    console.log(data);
                    alert('댓글이 삭제 되었습니다.');
                    li.remove();
                })
                .catch(err => console.log(err));
        }
    }else if(btnValue === "취소"){
        const textarea = e.target.parentElement.querySelector('textarea');
        const btnLeft = e.target;
        const btnRight = e.target.nextElementSibling;

        textarea.readOnly = true;
        textarea.style.outline = "none"
        btnLeft.innerText = "삭제"
        btnRight.innerText = "수정"

    }else if(btnValue === "수정"){
        // 수정모드 전환
        const textarea = e.target.parentElement.querySelector('textarea');
        const btnRight = e.target;
        const btnLeft = e.target.previousElementSibling;

        textarea.readOnly = false;
        textarea.style.outline = "1px solid #111"
        textarea.focus();
        textarea.setSelectionRange(textarea.value.length, textarea.value.length);

        // 버튼 수정
        btnLeft.innerText = "취소"
        btnRight.innerText = "저장"

    }else if(btnValue === "저장"){
        // 저장하기
        const no = e.target.dataset.no;
        const textarea = e.target.parentElement.querySelector('textarea');
        const div = e.target.parentElement.querySelector('.nameDate');
        const content = textarea.value;
        const btnRight = e.target;
        const btnLeft = e.target.previousElementSibling;

        console.log(textarea);
        console.log("div : "+div.value);


        await fetch(`/sboard/article/commentModify/${no}/${content}`)
            .then(response => {
                if(!response){
                    throw new Error('댓글 수정에 실패했습니다.');
                }
                return response.json();
            })
            .then(data => {
                console.log(data.content);
                console.log(textarea);

                const date = data.rdate.substring(0, 10);
                textarea.innerText = `${data.content}`; // 여기만 수정 하면 됨!!!!
                div.innerText = `${data.writer} | ${date}`;
                textarea.readOnly = true;
                textarea.style.outline = "none"
                btnLeft.innerText = "삭제"
                btnRight.innerText = "수정"

            })
            .catch(err => console.log(err))
    }
});


/// 삭제 / 취소
const btnCommentLeft = document.querySelectorAll('.btnCommentLeft');
// 수정 / 저장
const btnCommentRight = document.querySelectorAll('.btnCommentRight');

// 삭제 & 취소 이벤트
btnCommentLeft.forEach(function (item) {
    item.addEventListener('click', async function (e) {

        const btnValue = e.target.textContent;
        console.log(btnValue);
        if(btnValue === "삭제") {
            const no = e.target.dataset.no;
            const li = e.target.parentElement;

            const result = confirm("정말 삭제하시겠습니까?");
            if(result){
                await fetch(`/sboard/article/commentDelete/${no}`)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('댓글 삭제에 실패했습니다.');
                        }
                        return response.json()
                    })
                    .then(data => {
                        console.log(data);
                        alert('댓글이 삭제 되었습니다.');
                        li.remove();
                    })
                    .catch(err => console.log(err));
            }
        }else if(btnValue === "취소"){
            const textarea = e.target.parentElement.querySelector('textarea');
            const btnLeft = e.target;
            const btnRight = e.target.nextElementSibling;

            textarea.readOnly = true;
            textarea.style.outline = "none"
            btnLeft.innerText = "삭제"
            btnRight.innerText = "수정"
        }
    });
});

// 수정 & 저장
btnCommentRight.forEach(function (item) {
    item.addEventListener('click', async function (e) {

        const btnValue = e.target.textContent;
        console.log(btnValue);
        if(btnValue === "수정"){
            // 수정모드 전환
            const textarea = e.target.parentElement.querySelector('textarea');
            const btnRight = e.target;
            const btnLeft = e.target.previousElementSibling;

            textarea.readOnly = false;
            textarea.style.outline = "1px solid #111"
            textarea.focus();
            textarea.setSelectionRange(textarea.value.length, textarea.value.length);

            // 버튼 수정
            btnLeft.innerText = "취소"
            btnRight.innerText = "저장"

        }else if(btnValue === "저장"){
            // 저장하기
            const no = e.target.dataset.no;
            const textarea = e.target.parentElement.querySelector('textarea');
            const div = e.target.parentElement.querySelector('.nameDate');
            const content = textarea.value;
            const btnRight = e.target;
            const btnLeft = e.target.previousElementSibling;

            console.log(textarea);
            console.log("div : "+div.value);


            await fetch(`/sboard/article/commentModify/${no}/${content}`)
                .then(response => {
                    if(!response){
                        throw new Error('댓글 수정에 실패했습니다.');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log(data.content);
                    console.log(textarea);

                    const date = data.rdate.substring(0, 10);
                    textarea.innerText = `${data.content}`; // 여기만 수정 하면 됨!!!!
                    div.innerText = `${data.writer} | ${date}`;
                    textarea.readOnly = true;
                    textarea.style.outline = "none"
                    btnLeft.innerText = "삭제"
                    btnRight.innerText = "수정"

                })
                .catch(err => console.log(err))


        }
    });
});
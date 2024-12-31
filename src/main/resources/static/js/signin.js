'use strict';

// 로그인
window.onload = function () {
    console.log('현재 경로:', window.location.pathname);

    signin();
}




function signin() {
    // form submit 차단
    const $form = document.getElementById("form-signin");
    $form.addEventListener("submit", (e) => {
        e.preventDefault();
    });

    // authentication
    const $btnSignin = document.getElementById("btn-signin");
    $btnSignin.addEventListener("click", () => {
        // validation
        const id = document.getElementById("userId");
        const pw = document.getElementById("password");
        if (id.value == "" || pw.value == "") return alert("필수 입력 사항입니다.");

        // 1) request
        const payload = new FormData($form);

        let url = "/api/signin";
        fetch(url, {
            method: "post"
            , credentials: "include"
            , headers: {
                // "Content-Type":"application/json"
                'Content-Type': 'application/x-www-form-urlencoded'
            }
            , cache: 'no-cache'
            ,redirect: 'manual' // 리다이렉션 방지
            , body: new URLSearchParams(payload)
        })
            .then((res) => res.json())
            .then((data) => {
                console.log(data);

            })
            .catch(err => {
                console.log("err? " + err);

            })

        // // 2) href
        // .then(res => {
        //     return res.json();
        // }
        // ).then(data => {
        //     console.log("data 형태 asdf: " + data);
        //     if (data.resultCd === 'login_failed') return alert(data.resultMsg);
        //     location.href = '/';
        // })

        // // 3) exception
        // .catch(err => {
        //     console.log("err? "+ err);
        //     return alert("오류가 발생했습니다.");
        // });


        // 입력창 초기화
        // id.value = '';
        pw.value = "";

    });
}



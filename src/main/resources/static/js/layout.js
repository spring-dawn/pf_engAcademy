'use strict';

window.onload = function(){
    console.log("asdf 기본 레이아웃 동작");
    toggleMenuList();
};



/**
 * 메뉴 클릭 시 하위 메뉴 펼치기
 */
function toggleMenuList(){
    const rootList = document.querySelectorAll(".menu.root");
    rootList.forEach((rm) => {
        rm.addEventListener("click", (e)=>{
            let subMenu = rm.nextElementSibling;
            subMenu.classList.toggle("off");
            subMenu.classList.toggle("on");

        });
    })
    
}
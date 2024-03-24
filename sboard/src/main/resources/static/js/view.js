
//// 검색 ////
const btnNavbarSearch = document.getElementById('btnNavbarSearch');
const searchCate = document.getElementsByName('searchCate')[0];
const searchText = document.getElementsByName('searchText')[0];

function handleNavbarSearch() {
    alert('22');
    const cate = searchCate.value;
    const title = searchText.value;

    const url = `/sboard/article/list?cate=${cate}&title=${title}`;
    window.location.href = url;
}

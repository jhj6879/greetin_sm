function delFile(button){
	button.parentElement.remove();
}

function addFile(button) {
	const parent = button.parentElement;
	var addEl = document.createElement('p');
	addEl.innerHTML = '<input type="file" name="file">'
					+ '<input type="button" value="X" onclick="delFile(this)">';
	parent.appendChild(addEl);
}

// 페이징 검색기능
function search(button){
//	var boardno = button.getAttribute('data-boardno');
    var keyword = document.getElementById("keyword").value;
    location.href="/notice?keyword=" + keyword;;  // 적절한 경로로 이동
}
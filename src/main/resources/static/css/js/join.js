//아이디체크
function checkId() {
	var snd_data = $("#user_id").val();
	$.ajax(
		{
			type: "get",
			dataType: "text",
			async: true, //true : 비동기방식 - 페이지 갱신 X
			url: "http://localhost:8080/checkid",
			data: { data: snd_data }, //뷰에서 서버로 넘어가는 데이터
			success: function(data, textStatus) {
				if (data == "true") {
					$("#id-area").html("<p>사용 가능한 아이디입니다.</p>");
				} else {
					$("#id-area").html("<p>사용할 수 없는 아이디입니다.</p>");
				}

			},
			error: function(data, textStatus) {
				window.alert("에러가 발생했습니다.");
				window.alert(textStatus);
			},
			complete: function(data, textStatus) {
				//window.alert("작업을 완료했습니다.");
			}
		}
	);
}

function checkFormjoin() {
    // 유효성 검사
    const from = document.join;
    const userid = document.getElementById('user_id').value;
    const password = document.getElementById('user_pw').value;
    const employee_id = document.getElementById('employee_id').value;
    const user_name = document.getElementById('user_name').value;
    const r_num = document.getElementById('r_num').value;
    const tel = document.getElementById('tel').value;
    const address = document.getElementById('address').value;
    const email = document.getElementById('email').value;
    const genderM = document.getElementById('genderM').checked;
    const genderF = document.getElementById('genderF').checked;
    const department = document.getElementById('department').value;
    const position = document.getElementById('position').value;

    if (userid.length < 3 || userid.length > 8) {
        window.alert("아이디는 3~8자로 입력해주세요.");
        document.getElementById('user_id').focus();
        return;
    }
    if (user_pw.length < 4 || password.length > 20) {
        window.alert("패스워드는 4~20자로 입력해주세요.");
        document.getElementById('user_pw').focus();
        return;
    }
    if (employee_id == "") {
        window.alert("사번을 입력해주세요.");
        document.getElementById('employee_id').focus();
        return;
    }
    if (user_name == "") {
        window.alert("이름을 입력해주세요.");
        document.getElementById('user_name').focus();
        return;
    }
    if (r_num == "") {
        window.alert("주민등록번호를 입력해주세요.");
        document.getElementById('r_num').focus();
        return;
    }
    if (tel == "") {
        window.alert("연락처를 입력해주세요.");
        document.getElementById('tel').focus();
        return;
    }
    if (address == "") {
        window.alert("주소를 입력해주세요.");
        document.getElementById('address').focus();
        return;
    }
    if (email == "") {
        window.alert("이메일을 입력해주세요.");
        document.getElementById('email').focus();
        return;
    }
    if (!genderM && !genderF) {
        window.alert("성별을 선택해주세요.");
        return;
    }
    if (department == "") {
        window.alert("부서를 선택해주세요");
        document.getElementById('department').focus();
        return;
    }
    if (position == "") {
        window.alert("직책을 입력해주세요.");
        document.getElementById('position').focus();
        return;
    }
    window.alert("회원가입이 완료되었습니다.");
    from.submit(); // 서버 연동
};


function resetInout(){
	const from = document.join;

	window.alert("정보를 지우고 처음부터 다시 입력합니다!");
	from.user_id.focus();
	from.reset();
}

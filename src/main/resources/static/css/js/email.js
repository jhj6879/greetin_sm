// 문의글 저장버튼
function sendMail(){
	const form = document.inquireForm;
	var subject;
	const to = "jo39382355@gmail.com";
	var text = `
	<table>
	<tr><td>제목</td><td>${form.title.value}</td></tr>
	<tr><td>이메일</td><td>${form.email.value}</td></tr>
	<tr><td>이름</td><td>${form.name.value}</td></tr>
	<tr><td>전화번호</td><td>${form.telno.value}</td></tr>
	<tr><td>내용</td><td>${form.content.value}</td></tr>
	</table>
	`;

	subject = form.name.value + "님이 문의하신 내용입니다.";

	var subjectInput = document.createElement('input');
	subjectInput.type = 'hidden';
	subjectInput.name = 'subject';
	subjectInput.value = subject;

	var toInput = document.createElement('input');
	toInput.type = 'hidden';
	toInput.name = 'to';
	toInput.value = to;

	var textInput = document.createElement('input');
	textInput.type = 'hidden';
	textInput.name = 'text';
	textInput.value = text;

	form.appendChild(subjectInput);
	form.appendChild(toInput);
	form.appendChild(textInput);

	form.submit();
}

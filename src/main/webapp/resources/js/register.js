$('.summernote').summernote({
	tabsize: 2,
	height: 300
});
var today = new Date();  // 현재 날짜를 가져오기
var tomorrow = new Date(today);  // 내일 날짜 계산
tomorrow.setDate(today.getDate()+1);
var formattedDate = tomorrow.toISOString().split('T')[0]; // 날짜를 'YYYY-MM-DD' 형식으로 포맷팅
var deleteDateInput = document.getElementById('deleteDate'); // input 요소 찾기
deleteDateInput.min = formattedDate; // min 속성 설정
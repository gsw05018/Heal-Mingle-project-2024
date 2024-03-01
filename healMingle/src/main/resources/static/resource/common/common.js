// toastr 라이브러리의 설정 옵션을 정의합니다.
toastr.options = {
	closeButton: true, // 알림에 닫기 버튼을 활성화합니다.
	debug: false, // 디버그 모드를 비활성화합니다.
	newestOnTop: true, // 새 알림을 상단에 표시합니다.
	progressBar: true, // 알림에 진행 상태 바를 표시합니다.
	positionClass: "toast-top-right", // 알림의 위치를 화면의 오른쪽 상단으로 설정합니다.
	preventDuplicates: false, // 중복 알림을 방지하지 않습니다.
	onclick: null, // 알림 클릭 시 실행할 함수를 null로 설정합니다. 즉, 클릭 이벤트가 없음을 의미합니다.
	showDuration: "300", // 알림이 나타나는 데 걸리는 시간을 300ms로 설정합니다.
	hideDuration: "1000", // 알림이 사라지는 데 걸리는 시간을 1000ms로 설정합니다.
	timeOut: "5000", // 알림이 자동으로 사라지기까지의 시간을 5000ms로 설정합니다.
	extendedTimeOut: "1000", // 알림에 마우스를 올렸을 때 사라지기까지 추가로 대기하는 시간을 1000ms로 설정합니다.
	showEasing: "swing", // 알림이 나타날 때의 easing 효과를 "swing"으로 설정합니다.
	hideEasing: "linear", // 알림이 사라질 때의 easing 효과를 "linear"으로 설정합니다.
	showMethod: "fadeIn", // 알림이 나타나는 방식을 "fadeIn" 효과로 설정합니다.
	hideMethod: "fadeOut" // 알림이 사라지는 방식을 "fadeOut" 효과로 설정합니다.
};

// 성공 알림을 표시하는 함수를 정의합니다.
function toastNotice(msg) {
	toastr["success"](msg, "알림"); // toastr 라이브러리의 success 메서드를 사용하여 "알림" 제목으로 성공 메시지를 표시합니다.
}

// 경고 알림을 표시하는 함수를 정의합니다.
function toastWarning(msg) {
	toastr["warning"](msg, "알림"); // toastr 라이브러리의 warning 메서드를 사용하여 "알림" 제목으로 경고 메시지를 표시합니다.
}


// 메시지의 유효성을 파싱하는 함수를 정의합니다.
function parseMsg(msg) {
    const [pureMsg, ttl] = msg.split(";ttl="); // 메시지를 ";ttl=" 기준으로 분리하여 순수 메시지와 ttl(time-to-live) 값을 추출합니다.

    const currentJsUnixTimestamp = new Date().getTime(); // 현재 시간의 Unix 타임스탬프를 밀리초 단위로 가져옵니다.

    // ttl 값이 있고, 파싱된 ttl 값에 5000ms를 더한 값이 현재 시간보다 작다면 메시지가 만료된 것으로 판단합니다.
    if (ttl && parseInt(ttl) + 5000 < currentJsUnixTimestamp) {
        return [pureMsg, false]; // 만료된 경우 순수 메시지와 함께 false를 반환합니다.
    }

    return [pureMsg, true]; // 만료되지 않았다면 순수 메시지와 함께 true를 반환합니다.
}
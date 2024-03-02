package com.sideproject.healMingle.boundContext.genFile.service;

import com.sideproject.healMingle.base.ut.Ut;
import com.sideproject.healMingle.boundContext.genFile.entity.GenFile;
import com.sideproject.healMingle.boundContext.genFile.repository.GenFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
// 이 클래스를 스프링 서비스 계층의 컴포넌트로 선언합니다.
@RequiredArgsConstructor
// Lombok 라이브러리를 사용해 필수 생성자를 자동으로 생성합니다. 여기서는 genFileRepository 필드에 대한 생성자가 생성됩니다.
@Transactional(readOnly = true)
// 클래스 수준에서 트랜잭션 설정을 읽기 전용으로 설정합니다. 데이터 조회 작업에서 사용됩니다.
public class GenFileService {
	private final GenFileRepository genFileRepository;
	// GenFile 엔티티에 대한 CRUD 작업을 수행하는 레포지토리를 주입받습니다.

	@Transactional // save 메서드에 대한 트랜잭션을 설정합니다. 클래스 수준에서 설정한 readOnly=true를 오버라이드하여 이 메서드가 데이터 변경 작업을 포함한다는 것을 나타냅니다.
	public GenFile save(String relTypeCode, Long relId, String typeCode, String type2Code, int fileNo, MultipartFile multipartFile) {
		String originFileName = multipartFile.getOriginalFilename(); // 업로드된 파일의 원본 파일 이름을 가져옵니다.
		String fileExt = Ut.file.getExt(originFileName); // 파일 확장자를 추출합니다.
		String fileExtTypeCode = Ut.file.getFileExtTypeCodeFromFileExt(fileExt); // 파일 확장자로부터 파일 확장자 타입 코드를 가져옵니다.
		String fileExtType2Code = Ut.file.getFileExtType2CodeFromFileExt(fileExt); // 파일 확장자로부터 파일 확장자 타입2 코드를 가져옵니다.
		int fileSize = (int) multipartFile.getSize(); // 파일 크기를 가져옵니다.
		String fileDir = getCurrentDirName(relTypeCode); // 파일이 저장될 디렉토리 이름을 생성합니다.

		GenFile genFile = GenFile.builder() // GenFile 엔티티의 빌더를 사용하여 인스턴스를 생성합니다.
				.relTypeCode(relTypeCode)
				.relId(relId)
				.typeCode(typeCode)
				.type2Code(type2Code)
				.fileExtTypeCode(fileExtTypeCode)
				.fileExtType2Code(fileExtType2Code)
				.originFileName(originFileName)
				.fileSize(fileSize)
				.fileNo(fileNo)
				.fileExt(fileExt)
				.fileDir(fileDir)
				.build();

		genFileRepository.save(genFile); // 생성된 GenFile 엔티티를 데이터베이스에 저장합니다.

		File file = new File ( genFile.getFilePath() ); // GenFile 객체에서 파일이 저장될 전체 경로를 가져와 File 객체를 생성
		// genFile.getFilePath() : 메서드를 호출하여 파일이 저장될 경로 얻는다

		file.getParentFile ().mkdirs (); // 파잉리 저장되기 전에 파일이 저장될 부모 디렉터리(풀더)가 존재하는지 확인하고
		// 없으면 해당 경로에 풀더 생성
		// mkdirs : java.io.file 클래스의 메서드 중 하나로 지정된 경로에 풀더를 생성하는데 사용
		// 지정된 경로의 마지막 요소에 해당하는 다렉터리 뿐만 아니라 필요한 모든 부모 디렉토리도 함께 생성이 가능하다

		try {
			multipartFile.transferTo ( file );
			// multipartFile 객체를 사용하여 클라이언트로부터 받은 파일을 서버에 저장
		} catch ( IOException e ){
			throw new RuntimeException ( e );
			// 파일 전송 중 IOException 발생할 경우 런타임 에외로 포장하여 다시 던진다
		}

		return genFile; // 저장된 GenFile 엔티티를 반환합니다.
	}

	private String getCurrentDirName(String relTypeCode) { // 파일이 저장될 디렉토리 이름을 생성하는 메서드입니다.
		return relTypeCode + "/" + Ut.date.getCurrentDateFormatted("yyyy_MM_dd"); // 디렉토리 이름은 관계 타입 코드와 현재 날짜로 구성됩니다.
	}
}

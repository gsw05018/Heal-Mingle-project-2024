package com.sideproject.healMingle.boundContext.genFile.repository;

import com.sideproject.healMingle.boundContext.genFile.entity.GenFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenFileRepository extends JpaRepository<GenFile, Long > {

	List < GenFile > findByRelTypeCodeAndRelIdOrderByTypeCodeAscType2CodeAscFileNoAsc ( String relTypeCode, Long relId );
	// 여러 개의 리스트 형태로 오름차순으로 정렬하여 조회후 반환

	Optional < GenFile > findByRelTypeCodeAndRelIdAndTypeCodeAndType2CodeAndFileNo ( String relTypeCode, long relId, String typeCode, String type2Code, int fileNo );
	// relTypeCode, relId, typeCode, type2Code, fileNo에 정확히 일치하는 하나의 GenFile 엔티티 조회

	List < GenFile > findAllByRelTypeCodeAndRelIdInOrderByTypeCodeAscType2CodeAscFileNoAsc ( String relTypeCode, long[] relIds );
	// relTypeCode와 relId 배열에 포함된 아이디들에 해당하는 GenFile 엔티티들을 typeCode, type2Code, fileNo의 오름차순으로 정렬하여 조회


}
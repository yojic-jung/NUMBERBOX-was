package com.numberbox.datarepository.image;

import com.numberbox.appdomain.dto.image.ImgFileMetaInfoDto;
import com.numberbox.appusecase.image.port.out.repository.TmpImgFileRepository;
import com.numberbox.common.entity.TmpImgFileInfo;
import com.numberbox.datarepository.config.AbstractEntityManager;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TmpImgFileJpaRepository extends AbstractEntityManager implements TmpImgFileRepository {

    @Override
    public void save(UUID userId, ImgFileMetaInfoDto imgFileMetaInfoDto) {
        TmpImgFileInfo tmpImgFileInfoEntity = new TmpImgFileInfo(userId, imgFileMetaInfoDto.actionId(),
                imgFileMetaInfoDto.imgPathCode(), imgFileMetaInfoDto.imgPath(), imgFileMetaInfoDto.imgFileName());
        entityManager.persist(tmpImgFileInfoEntity);
    }
}

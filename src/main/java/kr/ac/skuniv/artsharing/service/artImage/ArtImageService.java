package kr.ac.skuniv.artsharing.service.artImage;

import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.artImage.ArtImage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtImageService {

    private static final String IMAGE_PATH = "resources.image-locations";


    private final Environment environment;


    public ArtImage saveImage(MultipartFile file, Art art) {

        UUID uid = UUID.randomUUID();
        String fileName = uid + "_" + file.getOriginalFilename();
        String savePath = makePath(environment.getProperty(IMAGE_PATH));
        File destinationFile = new File(environment.getProperty(IMAGE_PATH) + savePath, fileName);
        try {
            file.transferTo(destinationFile);

            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/artSharing/art/image/" + art.getId())
                    .toUriString();

            ArtImage artImage =
                    ArtImage.builder()
                            .imageName(file.getOriginalFilename())
                            .imageSize(file.getSize())
                            .imageType(file.getContentType())
                            .imageUrl(imageUrl)
                            .imagePath(environment.getProperty(IMAGE_PATH) + savePath + File.separator + fileName)
                            .art(art)
                            .build();

            art.updateArtImage(artImage);
            return artImage;
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

        return null;
    }

    private String makePath(String uploadPath) {

        Calendar calendar = Calendar.getInstance();

        String yearPath = File.separator + calendar.get(Calendar.YEAR);
        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.MONTH)+1);
        String datePath = monthPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.DATE));

        makeDir(uploadPath, yearPath, monthPath, datePath);

        return datePath;

    }

    private void makeDir(String uploadPath, String... paths) {

        if(new File(uploadPath + paths[paths.length - 1]).exists()) {
            return;
        }

        for( String path : paths){
            File dirPath = new File(uploadPath + path);

            if(!dirPath.exists()){
                dirPath.mkdir();
            }
        }
    }

}

package wcs.cerebook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wcs.cerebook.entity.*;
import wcs.cerebook.model.SimpleHostedMedia;
import wcs.cerebook.repository.*;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediaService {
    @Autowired
    PictureRepository pictureRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    EventRepository eventRepository;
    @Autowired
    PostRepository repository;

    @Autowired
    HostingService hostingService;

    public void uploadPicture(String filename, InputStream inputStream, long size, CerebookUser user) {
        hostingService.uploadPictureImage(filename, inputStream, size);

        CerebookPicture cerebookPicture = new CerebookPicture();
        cerebookPicture.setUser(user);
        cerebookPicture.setMediaType(CerebookPicture.Type.SimpleMedia);
        cerebookPicture.setPicturePath(filename);
        cerebookPicture.setAmazonS3Hosted(hostingService.isAmazon());

        pictureRepository.save(cerebookPicture);
    }

    public void uploadMovie(String filename, InputStream inputStream, long size, CerebookUser user) {
        hostingService.uploadPictureImage(filename, inputStream, size);

        CerebookMovie cerebookMovie = new CerebookMovie();
        cerebookMovie.getActors().add(user);
        cerebookMovie.setMediaType(CerebookPicture.Type.SimpleMedia);
        cerebookMovie.setMoviePath(filename);
        cerebookMovie.setAmazonS3Hosted(hostingService.isAmazon());

        movieRepository.save(cerebookMovie);
    }

    public void uploadVideo(String filename, InputStream inputStream, long size, CerebookUser user) {
        hostingService.uploadPictureImage(filename, inputStream, size);

        CerebookVideo cerebookVideo = new CerebookVideo();
        cerebookVideo.setUser(user);
        cerebookVideo.setMediaType(CerebookVideo.Type.SimpleMedia);
        cerebookVideo.setVideoPath(filename);
        cerebookVideo.setAmazonS3Hosted(hostingService.isAmazon());

        videoRepository.save(cerebookVideo);
    }

    public void uploadEventImage(String filename, InputStream inputStream, long size, CerebookUser user, CerebookEvent cerebookEvent) {
        hostingService.uploadPictureImage(filename, inputStream, size);

        cerebookEvent.setCerebookUser(user);
        cerebookEvent.setMediaType(CerebookEvent.Type.SimpleMedia);
        cerebookEvent.setImage(filename);
        cerebookEvent.setAmazonS3Hosted(hostingService.isAmazon());

        eventRepository.save(cerebookEvent);

    }
    public void uploadPostImage(String filename, InputStream inputStream, long size,CerebookUser user,CerebookPost cerebookPost){
        hostingService.uploadPictureImage(filename, inputStream, size);
        cerebookPost.setMediaType(CerebookPost.Type.SimpleMedia);
        cerebookPost.setCerebookUser(user);
        cerebookPost.setImage(filename);
        cerebookPost.setAmazonS3Hosted(hostingService.isAmazon());
        repository.save(cerebookPost);
    }

    public List<SimpleHostedMedia> getMediaList() {
        return pictureRepository
                .findAll()
                .stream()
                .map(cm -> new SimpleHostedMedia(
                        hostingService.getUrlPrefix(),
                        cm
                ))
                .collect(Collectors.toList());
    }

    public void uploadBanner(String bannerName, InputStream inputStream, long size, CerebookUser user) {
                hostingService.uploadPictureImage(bannerName, inputStream, size);
                user.getProfil().setMediaType(CerebookProfil.Type.SimpleMedia);
                user.getProfil().setBanner(bannerName);
                user.getProfil().setAmazonS3Hosted(hostingService.isAmazon());
    }
}

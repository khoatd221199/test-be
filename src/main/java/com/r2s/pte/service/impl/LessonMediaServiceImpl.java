package com.r2s.pte.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.r2s.pte.common.File;
import com.r2s.pte.dto.LessonAudioCreateDTO;
import com.r2s.pte.dto.LessonMediaDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.entity.Lesson;
import com.r2s.pte.entity.LessonMedia;
import com.r2s.pte.mapper.LessonMediaMapper;
import com.r2s.pte.repository.LessonMediaRepository;
import com.r2s.pte.service.LessonMediaService;
import com.r2s.pte.util.FileHandle;

@Service
@Transactional
public class LessonMediaServiceImpl implements LessonMediaService {
	@Autowired
	private LessonMediaRepository lessonMediaRepository;
	@Autowired
	private LessonMediaMapper lessonMediaMapper;
	@Autowired
	private FileHandle fileHandle;
	private final String TYPE_AUDIO = "audio";
	private final String TYPE_IMAGE = "image";
	private final String TYPE_VIDEO = "video";
	private final String TYPE_SHADOWING = "shadowing";
	private final String[] ATTRIBUTES_IMAGE = { "jpeg", "jpg", "png", "gif", "pdf", "psd", "tiff" };
	private final String[] ATTRIBUTES_AUDIO = { "mp3", "wma", "wav", "flac" };
	private final String[] ATTRIBUTES_VIDEO = { "avi", "mp4", "mkv", "wmv" };

	@Override
	public LessonMediaDTO save(LessonMediaDTO lessonMediaDTO, Lesson lessonSaved) {
		if (lessonMediaDTO != null && !lessonMediaDTO.getLessonMediaLink().equals("")) {
			LessonMedia lessonMedia = lessonMediaMapper.mapToEntity(lessonMediaDTO);
			setTime(lessonMedia);
			lessonMedia.setLesson(lessonSaved);
			setLinkMediaAndType(lessonMedia);
			LessonMedia saved = this.lessonMediaRepository.save(lessonMedia);
			return lessonMediaMapper.mapToDTO(saved);
		}
		return null;

	}

	public void update(LessonMediaDTO dto, Lesson lessonSaved, LocalDateTime createDate, Long createBy) {
		LessonMedia lessonMedia = lessonMediaMapper.mapToEntity(dto);
		lessonMedia.setCreatedBy(createBy);
		lessonMedia.setCreatedDate(createDate);
		lessonMedia.setModifiedBy(UserContext.getId());
		lessonMedia.setModifiedDate(LocalDateTime.now());
		lessonMedia.setLesson(lessonSaved);
		setLinkMediaAndType(lessonMedia);
		this.lessonMediaRepository.save(lessonMedia);
	}

	@Override
	public void update(List<LessonMediaDTO> lessonMediaDTOs, Lesson lesson) {
		Long lessonId = lesson.getId();
		List<LessonMedia> lessonMedias = this.lessonMediaRepository.findByLessonId(lessonId);
		if (lessonMedias != null && lessonMedias.size() > 0) {
			int firstIndex = 0;
			LocalDateTime createTime = lessonMedias.get(firstIndex).getCreatedDate();
			Long createBy = lessonMedias.get(firstIndex).getCreatedBy();
			deleteByLesson(lessonId);
			lessonMediaDTOs.forEach(dto->{
				update(dto, lesson, createTime, createBy);
			});
		} else {
			save(lessonMediaDTOs, lesson);
		}
	}

	private void setLinkMediaAndType(LessonMedia lessonMedia) {
		String link = null;
		String lessonMediaLink = lessonMedia.getLessonMediaLink();
		if (lessonMediaLink != null && !lessonMediaLink.equals("")) {
			String attribute = getType(lessonMediaLink);
			if (attribute.equals(TYPE_AUDIO))
				link = setLinkMedia(lessonMediaLink);
			else
				link = lessonMediaLink;

			lessonMedia.setLessonMediaLink(link);
			lessonMedia.setType(attribute);
		}
	}

	private String getType(String link) {
		if (link != null && !link.equals("")) {
			String attributeFile = fileHandle.getAttributeFile(link).substring(1);
			if (checkAudio(attributeFile) != null)
				return checkAudio(attributeFile);
			else if (checkVideo(attributeFile) != null)
				return checkVideo(attributeFile);
			else if (checkImage(attributeFile) != null)
				return checkImage(attributeFile);
			else
				return TYPE_SHADOWING;
		}
		return null;
	}

	private String checkImage(String attribute) {
		if (Arrays.asList(ATTRIBUTES_IMAGE).contains(attribute))
			return TYPE_IMAGE;
		return null;
	}

	private String checkAudio(String attribute) {
		if (Arrays.asList(ATTRIBUTES_AUDIO).contains(attribute))
			return TYPE_AUDIO;
		return null;
	}

	private String checkVideo(String attribute) {
		if (Arrays.asList(ATTRIBUTES_VIDEO).contains(attribute))
			return TYPE_VIDEO;
		return null;
	}

	@Override
	public void save(List<LessonMediaDTO> lessonMediaDTOs, Lesson lessonSaved) {
		lessonMediaDTOs.forEach(lessonMedia -> {
			save(lessonMedia, lessonSaved);
		});
	}

	private void setTime(LessonMedia lessonMedia) {
		LocalDateTime timeNow = LocalDateTime.now();
		lessonMedia.setCreatedDate(timeNow);
		lessonMedia.setCreatedBy(UserContext.getId());
		lessonMedia.setModifiedDate(timeNow);
		lessonMedia.setModifiedBy(UserContext.getId());
	}

	private String setLinkMedia(String url) {
		String link = this.fileHandle.setURLServer(url);
		return link;
	}

	public void uploadFile(LessonAudioCreateDTO lessonAudioDTO, MultipartFile image, MultipartFile shadowing) {
		if (image != null || shadowing != null) {
			List<LessonMediaDTO> lessonMediaDTOs = lessonAudioDTO.getLessonMedias();
			if (lessonMediaDTOs == null)
				lessonMediaDTOs = new ArrayList<LessonMediaDTO>();
			File fileImage = uploadFile(image);
			File fileShadowing = uploadFile(shadowing);
			addEntity(fileShadowing, lessonMediaDTOs);
			addEntity(fileImage, lessonMediaDTOs);
			lessonAudioDTO.setLessonMedias(lessonMediaDTOs);
		}
	}

	private File uploadFile(MultipartFile image) {
		File file = null;
		if (image != null) {
			file = new File();
			file.setFile(image);
			this.fileHandle.update(file);
		}
		return file;
	}

	private void addEntity(File file, List<LessonMediaDTO> lessonMediaDTOs) {
		if (file != null) {
			LessonMediaDTO lessonMediaDTO = new LessonMediaDTO();
			lessonMediaDTO.setLessonMediaLink(file.getFileName());
			lessonMediaDTOs.add(lessonMediaDTO);
		}
	}

	@Override
	public void deleteByLesson(Long lessonId) {
		List<LessonMedia> lessonMedias = this.lessonMediaRepository.findByLessonId(lessonId);
		if (lessonMedias != null && lessonMedias.size() > 0) {
			for (LessonMedia lessonMedia : lessonMedias) {
				lessonMedia.setLesson(null);
				this.lessonMediaRepository.save(lessonMedia);
				this.lessonMediaRepository.deleteById(lessonMedia.getId());
			}
		}
	}
}

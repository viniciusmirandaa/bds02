package com.devsuperior.bds02.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private CityRepository cityRepository;

	@Transactional
	public EventDTO update(Long id, EventDTO event) {
		try {
			Event entity = eventRepository.getOne(id);
			copyDtoToEntity(entity, event);
			entity = eventRepository.save(entity);
			return new EventDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id {" + id + "} not found");
		}
	}

	private void copyDtoToEntity(Event entity, EventDTO event) {
		entity.setName(event.getName());
		entity.setDate(event.getDate());
		entity.setUrl(event.getUrl());
		entity.setCity(cityRepository.getOne(event.getCityId()));
	}
}

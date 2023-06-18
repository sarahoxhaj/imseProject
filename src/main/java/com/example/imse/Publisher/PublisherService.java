package com.example.imse.Publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher findByPublisherName(String publisherName) {
        return publisherRepository.findByName(publisherName);
    }


}

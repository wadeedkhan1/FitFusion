package com.fitfusion.service;

import com.fitfusion.model.User;
import com.fitfusion.model.UserProfile;
import com.fitfusion.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public UserProfile getProfileByUserId(Long userId) {
        return userProfileRepository.findByUserId(userId).orElse(null);
    }

    public UserProfile saveOrUpdateProfile(UserProfile profile) {
        return userProfileRepository.save(profile);
    }

    public void createInitialProfile(User user) {
        UserProfile profile = new UserProfile();
        profile.setUser(user);
        userProfileRepository.save(profile);
    }
}

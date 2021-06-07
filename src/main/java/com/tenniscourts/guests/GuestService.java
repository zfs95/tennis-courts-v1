package com.tenniscourts.guests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public List<GuestDTO> findAllGuests(){
        return guestMapper.map(guestRepository.findAll());
    }

    public GuestDTO findGuestById(Long userId){
        Guest result = guestRepository.findById(userId).orElse(null);
        if(result == null){
            return null;
        }
        return guestMapper.map(guestRepository.findById(userId).get());
    }

    public GuestDTO findGuestByName(String userName){
        return guestMapper.map(guestRepository.findByName(userName));
    }

    public GuestDTO addGuest(GuestDTO guest){
        if(guest==null || guest.getName() == null){
            return null;
        }

        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guest)));
    }

    public GuestDTO updateGuest(GuestDTO guest){
        if(guest==null || guest.getId() == null || guest.getName() == null){
            return null;
        }
        Guest guestToUpdate = guestRepository.findById(guest.getId()).orElse(null);

        if(guest.getName()!=null && guestToUpdate!=null){
            guestToUpdate.setName(guest.getName());
            return guestMapper.map(guestRepository.saveAndFlush(guestToUpdate));
        }
        return null;
    }

    public GuestDTO deleteGuest(Long id){
        Guest guestToDelete = guestRepository.findById(id).get();
        Guest lastDeletedGuest = guestToDelete;
        guestRepository.delete(guestToDelete);
        return guestMapper.map(lastDeletedGuest);
    }
}

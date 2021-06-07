package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/guest")
@AllArgsConstructor
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @GetMapping(value = "/{userId}")
    public ResponseEntity<GuestDTO> getGuestById(@PathVariable Long userId){
        GuestDTO result = guestService.findGuestById(userId);
        if(result == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(guestService.findGuestById(userId));
    }

    @GetMapping(value = "/{userName}")
    public ResponseEntity<GuestDTO> getGuestByName(@PathVariable String userName){
        GuestDTO result = guestService.findGuestByName(userName);
        if(result == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(guestService.findGuestByName(userName));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<GuestDTO>> getAllGuests(){
        return ResponseEntity.ok(guestService.findAllGuests());
    }

    @PostMapping(path = "/add")
    public ResponseEntity<GuestDTO> addGuest(@RequestBody GuestDTO guest){
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guest).getId())).build();
    }

    @PatchMapping(value = "/update")
    public ResponseEntity<GuestDTO> updateGuest( @RequestBody GuestDTO guest){
        GuestDTO result = guestService.updateGuest(guest);
        if(result == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(guestService.updateGuest(guest));
    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<GuestDTO> deleteGuest(@PathVariable Long userId){
        GuestDTO result = guestService.findGuestById(userId);
        if(result == null){
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(guestService.deleteGuest(userId));
    }
}

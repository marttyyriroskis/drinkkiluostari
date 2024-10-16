package com.drinkkiluostari.backend.web;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.drinkkiluostari.backend.domain.Tyontekija;
import com.drinkkiluostari.backend.repository.TyontekijaRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final TyontekijaRepository tyontekijaRepository;

    public UserDetailServiceImpl(TyontekijaRepository tyontekijaRepository) {
        this.tyontekijaRepository = tyontekijaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String sahkoposti) throws UsernameNotFoundException {
        Tyontekija tyontekija = tyontekijaRepository.findBySahkoposti(sahkoposti);
        UserDetails user = new org.springframework.security.core.userdetails.User(sahkoposti, tyontekija.getSalasana(),
               AuthorityUtils.createAuthorityList(tyontekija.getRooli().getNimi()));
       return user;
    }
}

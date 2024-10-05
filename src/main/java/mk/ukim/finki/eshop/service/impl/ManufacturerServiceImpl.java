package mk.ukim.finki.eshop.service.impl;

import mk.ukim.finki.eshop.model.Manufacturer;
import mk.ukim.finki.eshop.repository.inmemory.InMemoryManufacturerRepository;
import mk.ukim.finki.eshop.repository.jpa.ManufacturerRepository;
import mk.ukim.finki.eshop.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        return manufacturerRepository.findById(id);
    }

    @Override
    public Optional<Manufacturer> save(String name, String address) {
        //return this.manufacturerRepository.save(name, address);
        Manufacturer manufacturer = new Manufacturer(name, address);
        return Optional.of(this.manufacturerRepository.save(manufacturer));

    }

//    @Override
//    public boolean deleteById(Long id) {
//        return this.manufacturerRepository.deleteById(id);
//    }


    @Override
    public void deleteById(Long id) {
        this.manufacturerRepository.deleteById(id);
    }

    @Override
    public boolean exists(Long id) {
        return this.manufacturerRepository.existsById(id);
    }
}

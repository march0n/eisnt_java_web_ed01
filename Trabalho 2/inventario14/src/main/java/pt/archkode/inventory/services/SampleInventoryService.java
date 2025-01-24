package pt.archkode.inventory.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pt.archkode.inventory.data.SampleInventory;
import pt.archkode.inventory.data.SampleInventoryRepository;

@Service
public class SampleInventoryService {

    private final SampleInventoryRepository repository;

    public SampleInventoryService(SampleInventoryRepository repository) {
        this.repository = repository;
    }

    public Optional<SampleInventory> get(Long id) {
        return repository.findById(id);
    }

    public SampleInventory update(SampleInventory entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<SampleInventory> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<SampleInventory> list(Pageable pageable, Specification<SampleInventory> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}

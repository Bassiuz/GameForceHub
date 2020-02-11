package com.bassiuz.gameforcehub.tools;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.wildfly.common.Assert;


import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class LocalRepositoryTest {

    @Test
    void save() {
        assertEquals(true, true);

        LocalRepository<Integer> integerRepository = new LocalRepository<>();
        integerRepository.add(1);
        integerRepository.add(2);
        integerRepository.add(3);

        //Should equal 3
        assertEquals(3, integerRepository.size());

        integerRepository.save(4,3);

        // Should be the same size.
        assertEquals(3, integerRepository.size());

        integerRepository.save(5, null);

        assertEquals(4, integerRepository.size());
    }
}
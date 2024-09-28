package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.infra.orm.annotation.LocalDBJpaTest
import org.springframework.beans.factory.annotation.Autowired

@LocalDBJpaTest
class MemberProfileReadOrmAdapterTest {
    @Autowired
    private lateinit var memberProfileReadOrmAdapter: MemberProfileReadOrmAdapter
}
package com.example.demo;

import com.example.demo.domain.model.Tree;
import com.example.demo.domain.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class DataBaseLoader {

    private final String ddlAuto;
    private final TreeRepository treeRepository;

    public DataBaseLoader(@Value("${spring.jpa.hibernate.ddl-auto}") String ddlAuto, TreeRepository treeRepository) {
        this.ddlAuto = ddlAuto;
        this.treeRepository = treeRepository;
    }

    @PostConstruct
    @Transactional
    public void loadData() {
        if (!Arrays.asList("update", "create", "create-drop").contains(ddlAuto)) {
            return;
        }

        Tree root = Tree.of("これは根だね");
        root = treeRepository.save(root);

        Tree first = Tree.of("第一子誕生")
                .withParent(root);
        first = treeRepository.save(first);

        Tree second = Tree.of("第二子誕生")
                .withParent(root);
        second = treeRepository.save(second);
    }
}

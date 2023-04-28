package tech.abc.platform.common.utils;

import tech.abc.platform.common.vo.TreeVO;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 树结构工具类
 *
 * @author wqliu
 * @date 2023-03-06
 */
@UtilityClass
public final class TreeUtil {

    /**
     * @param nodes    结构树所有节点
     * @param parentId 父节点
     * @param
     * @return
     */
    public static List<TreeVO> buildTree(List<TreeVO> nodes, String parentId) {
        // 获取所有的孩子节点
        List<TreeVO> childrenResult = getChildren(nodes, parentId);
        // 设置顶层节点
        Optional<TreeVO> topNode = nodes.stream().filter(tTree -> tTree.getId().equals(parentId)).findFirst();
        // 顶层节点不存在 直接返回所有的孩子节点
        if (!topNode.isPresent()) {
            return childrenResult;
        }
        TreeVO tree = new TreeVO();
        tree.setLabel(topNode.get().getLabel());
        tree.setId(topNode.get().getId());
        tree.setCode(topNode.get().getCode());
        tree.setExpand(true);
        tree.setParentId(parentId);
        tree.setChecked(topNode.get().getChecked());
        if (childrenResult != null && !childrenResult.isEmpty()) {
            tree.setChildren(childrenResult);
        }
        List<TreeVO> result = new ArrayList<>();
        result.add(tree);
        return result;

    }


    private static List<TreeVO> getChildren(List<TreeVO> nodes, String parentId) {

        List<TreeVO> result = new ArrayList<>();
        for (TreeVO node : nodes) {
            if (node.getParentId().equals(parentId)) {
                TreeVO tree = new TreeVO();
                tree.setLabel(node.getLabel());
                tree.setId(node.getId());
                tree.setExpand(true);
                tree.setParentId(parentId);
                tree.setChecked(node.getChecked());
                tree.setCode(node.getCode());
                List<TreeVO> childList = getChildren(nodes, node.getId());
                if (childList != null && !childList.isEmpty()) {
                    tree.setChildren(childList);
                }
                result.add(tree);
            }
        }
        return result;
    }
}

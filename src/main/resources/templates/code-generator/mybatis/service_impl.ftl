package ${packageName}.service.impl;

import ${packageName}.entity.*;
import ${packageName}.common.PageList;
import ${packageName}.mapper.*;
import ${packageName}.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层实现类
 * ${classInfo.classComment}ServiceImpl
 * @author ${authorName}
 * @date ${.now?string('yyyy/MM/dd')}
 */
@Service
public class ${classInfo.className}ServiceImpl implements ${classInfo.className}Service {

    @Autowired
	private ${classInfo.className}Mapper ${classInfo.className?substring(0,1)?lower_case}${classInfo.className?substring(1)}Mapper;

    @Override
    public int insert(${classInfo.className} ${classInfo.modelName}) {
        return ${classInfo.className?substring(0,1)?lower_case}${classInfo.className?substring(1)}Mapper.insert(${classInfo.modelName});
    }

    @Override
    public int batchInsert(List<${classInfo.className}> list) {
    	return ${classInfo.className?substring(0,1)?lower_case}${classInfo.className?substring(1)}Mapper.batchInsert(list);
    }

    @Override
    public int update(${classInfo.className} ${classInfo.modelName}) {
    	return ${classInfo.className?substring(0,1)?lower_case}${classInfo.className?substring(1)}Mapper.update(${classInfo.modelName});
    }

    @Override
    public int delete(Object key) {
    	return ${classInfo.className?substring(0,1)?lower_case}${classInfo.className?substring(1)}Mapper.delete(key);
    }

    @Override
    public int batchDelete(List<Object> keys) {
        return ${classInfo.className?substring(0,1)?lower_case}${classInfo.className?substring(1)}Mapper.batchDelete(keys);
    }

	@Override
	public ${classInfo.className} selectByKey(Object key) {
		return ${classInfo.className?substring(0,1)?lower_case}${classInfo.className?substring(1)}Mapper.selectByKey(key);
	}

	@Override
	public List<${classInfo.className}> selectList(${classInfo.className} ${classInfo.modelName}) {
		return ${classInfo.className?substring(0,1)?lower_case}${classInfo.className?substring(1)}Mapper.selectList(${classInfo.modelName});
	}

	@Override
	public PageList<${classInfo.className}> selectPage(${classInfo.className} ${classInfo.modelName}, Integer offset, Integer pageSize) {
		PageList<${classInfo.className}> pageList = new PageList<>();

		int total = this.total(${classInfo.modelName});

		Integer totalPage;
		if (total % pageSize != 0) {
			totalPage = (total /pageSize) + 1;
		} else {
			totalPage = total /pageSize;
		}

		int page = (offset - 1) * pageSize;

		List<${classInfo.className}> list = ${classInfo.className?substring(0,1)?lower_case}${classInfo.className?substring(1)}Mapper.selectPage(${classInfo.modelName}, page, pageSize);

		pageList.setList(list);
		pageList.setStartPageNo(offset);
		pageList.setPageSize(pageSize);
		pageList.setTotalCount(total);
		pageList.setTotalPageCount(totalPage);
		return pageList;
	}

	@Override
	public int total(${classInfo.className} ${classInfo.modelName}) {
		return ${classInfo.className?substring(0,1)?lower_case}${classInfo.className?substring(1)}Mapper.total(${classInfo.modelName});
	}
}
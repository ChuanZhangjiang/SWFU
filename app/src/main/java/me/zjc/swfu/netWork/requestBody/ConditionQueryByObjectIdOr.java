package me.zjc.swfu.netWork.requestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChuanZhangjiang on 2016/3/25.
 */
public class ConditionQueryByObjectIdOr {
    List<SubBody> $or;

    class SubBody {
        String objectId;
    }

    public void setSubBody (List<String> objectIdList) {
        $or = new ArrayList<>();
        for (String objectId: objectIdList) {
            SubBody subBody = new SubBody();
            subBody.objectId = objectId;
            $or.add(subBody);
        }
    }
}

import apiUtils from "../utils/apiUtils";

const MS = {
    getAll: ()=>{
        return apiUtils.get("/manufacturer/all");
    }
}
export default MS;
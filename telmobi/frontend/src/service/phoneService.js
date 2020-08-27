import apiUtils from "../utils/apiUtils";

const PS = {
    getAll:()=>{
        return apiUtils.get("/phone/all");
    },
    createPhone:(phoneForm)=>{
        return apiUtils.post("/phone/create",phoneForm);
    },
    
    findPhoneByPrice:(minPrice,maxPrice)=>{
        apiUtils.get("/phone/all/price/?minPrice="+minPrice+"&maxPrice="+maxPrice);
    },
    getPhone:(phoneId)=>{
        return apiUtils.get("/phone/" + phoneId);
    },
    deletePhone:(phoneId)=>{
        return apiUtils.delete("/phone/" + phoneId);
    },
    findPhoneByManufacturer:(phoneManufacturer)=>{
        return apiUtils.post("all/manufacturer" + phoneManufacturer);
    }

}

export default PS;
package com.exporum.core.domain.code.service;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

//@Service
//@RequiredArgsConstructor
public class CodeService {

//    private final CodeMapper codeMapper;
//
//    private final CountryService countryService;
//
//    public List<CodeList> findByCode(String code) {
//        return codeMapper.findByCode(code);
//    }
//
//
//    public OperationResponse findByCodeSet(String setCode) throws DataNotFoundException {
//
//        CodeSet codeSet = Optional.ofNullable(codeMapper.findCodeSetBySetCode(setCode)).orElseThrow(DataNotFoundException::new);
//
//        return switch (setCode) {
//            case "exhibitor-register", "user-register" ->
//                    new ContentsResponse<CountryWithRegisterSet>(OperationStatus.SUCCESS, CountryWithRegisterSet.builder()
//                            .codeList(codeMapper.findByCodeIn(codeSet.getCodes()))
//                            .countryList(countryService.getCountryList())
//                            .callingCodeList(countryService.getCallingCodeList())
//                            .build());
//            default -> new ContentsResponse<DefaultRegisterSet>(OperationStatus.SUCCESS, DefaultRegisterSet.builder()
//                    .codeList(codeMapper.findByCodeIn(codeSet.getCodes()))
//                    .build());
//        };
//    }
//
//    public CodeDetail getCode(String code) throws DataNotFoundException {
//        return Optional.ofNullable(codeMapper.getCode(code)).orElseThrow(DataNotFoundException::new);
//    }

}

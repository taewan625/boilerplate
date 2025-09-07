#!/bin/bash

# 입력 파라미터 확인
if [ -z "$1" ] || [ -z "$2" ]; then
  echo "Usage: $0 <admin|client> <PackageName>"
  exit 1
fi

PROJECT=$1       # admin 또는 client
PACKAGE_NAME=$2  # 패키지명
# 첫 글자를 대문자로 (클래스명에 사용)
CLASS_NAME="$(tr '[:lower:]' '[:upper:]' <<< ${PACKAGE_NAME:0:1})${PACKAGE_NAME:1}"

# 현재 날짜
DATE=$(date '+%Y. %-m. %-d.')

# 기본 경로 (프로젝트 루트 기준)
BASE_DIR="$PROJECT/src/main/java/com/exporum/$PROJECT/domain/$PACKAGE_NAME"

# 디렉토리 생성
mkdir -p "$BASE_DIR/controller"
mkdir -p "$BASE_DIR/service"
mkdir -p "$BASE_DIR/mapper"
mkdir -p "$BASE_DIR/model"

# 일반 Controller 생성
cat > "$BASE_DIR/controller/${CLASS_NAME}Controller.java" <<EOF
package com.exporum.$PROJECT.domain.$PACKAGE_NAME.controller;

import org.springframework.stereotype.Controller;

/**
 * ${CLASS_NAME}Controller.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @since $DATE 최초 작성
 */
@Controller
public class ${CLASS_NAME}Controller {
}
EOF

# RestController 생성
cat > "$BASE_DIR/controller/${CLASS_NAME}RestController.java" <<EOF
package com.exporum.$PROJECT.domain.$PACKAGE_NAME.controller;

import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

/**
 * ${CLASS_NAME}RestController.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @since $DATE 최초 작성
 */
@RestController
@RequiredArgsConstructor
public class ${CLASS_NAME}RestController {
}
EOF

# Service 생성
cat > "$BASE_DIR/service/${CLASS_NAME}Service.java" <<EOF
package com.exporum.$PROJECT.domain.$PACKAGE_NAME.service;

import org.springframework.stereotype.Service;

/**
 * ${CLASS_NAME}Service.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @since $DATE 최초 작성
 */
@Service
public class ${CLASS_NAME}Service {
}
EOF

# Mapper 생성 (interface)
cat > "$BASE_DIR/mapper/${CLASS_NAME}Mapper.java" <<EOF
package com.exporum.$PROJECT.domain.$PACKAGE_NAME.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * ${CLASS_NAME}Mapper.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @since $DATE 최초 작성
 */
@Mapper
public interface ${CLASS_NAME}Mapper {
}
EOF

echo "✅ Package structure for '$PACKAGE_NAME' created under $PROJECT/src/main/java/com/exporum/$PROJECT/domain/"

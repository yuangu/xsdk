pods=("xsdk_core" "xsdk_webchat") 
pod repo add-coding-ar pods_open https://seantone-cocoapods.pkg.coding.net/product/pods_open

for item in ${pods[@]}
do
   # pod lib lint ${item}/${item}.podspec --sources='https://cdn.cocoapods.org/,https://seantone-cocoapods.pkg.coding.net/product/pods_open' --skip-import-validation --allow-warnings --use-libraries --use-modular-headers 
    pod repo push-coding-ar pods_open  ${item}/${item}.podspec
done
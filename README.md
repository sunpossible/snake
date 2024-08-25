# 貪吃蛇遊戲

這是一個使用 Java 編寫的經典貪吃蛇遊戲專案。此專案實現了蛇的移動、食物生成、吃食物增長等功能，並包含了一個簡單的圖形使用者介面。

## 功能

- **蛇的移動**：玩家可以控制蛇在遊戲區域內上下左右移動。
- **食物生成**：遊戲中會隨機生成食物，當蛇吃到食物時，蛇的長度會增加。
- **碰撞檢測**：當蛇撞到牆壁或自己的身體時，遊戲結束。
- **暫停與繼續**：遊戲可以暫停或繼續進行。
- **簡單的使用者介面**：提供了一個基本的圖形介面供玩家與遊戲進行互動。

## 文件結構

- `Snake.java`：主遊戲邏輯，負責蛇的移動、碰撞檢測、食物生成等。
- `Coordinate.java`：定義了二維座標類，用於管理蛇的身體部分和食物位置。
- `Direction.java`：定義了移動方向的枚舉類，包括上下左右四個方向。
- `Scene.java`：管理遊戲場景的繪製。
- `Help.java`：提供遊戲幫助或說明。

## 開發環境

- **程式語言**：Java
- **IDE**：當前使用IntelliJ IDEA，推薦使用 IntelliJ IDEA 或 Eclipse
- **SDK**：當前使用Oracle OpenJDK version 21.0.1，推薦使用Java SE Development Kit (JDK) 8 或更高版本

## 待辦事項

- [ ] 添加更多的遊戲難度級別
- [ ] 實現排行榜功能
- [ ] 優化遊戲效能
- [ ] 改進圖形使用者介面

## 參與貢獻

歡迎貢獻程式碼和提出改進建議！您可以通過以下方式參與貢獻：

1. Fork 專案
2. 創建新分支 (`git checkout -b feature/your-feature`)
3. 提交更改 (`git commit -am 'Add some feature'`)
4. 推送至新分支 (`git push origin feature/your-feature`)
5. 創建新 Pull Request

---

感謝您對這個專案的關注！
ovd-xque-koq

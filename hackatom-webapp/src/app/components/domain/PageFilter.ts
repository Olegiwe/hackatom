export class PageFilter {
  fieldName: string;
  modeValMap = {};

  constructor(fieldName, values, matchModes) {
    this.fieldName = fieldName;
    for (let i = 0; i < values.length; i++) {
      if (values[i] != null) {
        if (values[i] instanceof Date) {
          this.modeValMap[matchModes[i]] = values[i].toLocaleDateString();
        } else {
          this.modeValMap[matchModes[i]] = values[i];
        }
      }
    }
  }
}

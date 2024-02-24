export default function SellNameSelector({ value, onchange, className }) {
  return (
    <select
      name="sellName-selector"
      id="sellName-selector"
      value={value}
      onChange={e => {
        onchange(e);
      }}
      className={className}
    >
      <option value="Genesis G70">Genesis G70</option>
      <option value="Genesis G80">Genesis G80</option>
      <option value="genesis g90">Genesis G90</option>
      <option value="Genesis GV60">Genesis GV60</option>
      <option value="Genesis GV70">Genesis GV70</option>
      <option value="Genesis GV80">Genesis GV80</option>
    </select>
  );
}

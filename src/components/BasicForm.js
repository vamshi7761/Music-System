import React, { useState } from 'react';

const BasicForm = () => {
  const [text, setText] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    alert(`Submitted: ${text}`);
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>Text:</label>
      <input type="text" value={text} onChange={(e) => setText(e.target.value)} required />
      <button type="submit">Submit</button>
    </form>
  );
};

export default BasicForm;
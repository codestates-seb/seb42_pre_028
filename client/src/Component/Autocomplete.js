/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/no-noninteractive-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable react/prop-types */
/* eslint-disable no-unused-vars */
import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { deselectedOptions } from './deselectedOptions';

const boxShadow = '0 4px 6px rgb(32 33 36 / 28%)';
const inactiveBorderRadius = '1rem 1rem 1rem 1rem';

export const TagsInput = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  min-height: 48px;
  padding: 0 8px;

  border-radius: 6px;

  > ul {
    display: flex;
    flex-wrap: wrap;
    padding: 0;
    margin: 8px 0 0 0;

    > .tag {
      width: auto;
      height: 32px;
      display: flex;
      align-items: center;
      justify-content: center;
      color: black;
      padding: 0 8px;
      font-size: 14px;
      list-style: none;
      border-radius: 6px;
      margin: 0 8px 8px 0;
      background: #e1ecf4;
      > .tag-close-icon {
        display: block;
        width: 16px;
        height: 16px;
        line-height: 16px;
        text-align: center;
        font-size: 14px;
        margin-left: 8px;
        color: var(--coz-purple-600);
        border-radius: 50%;
        background: #fff;
        cursor: pointer;
      }
    }
  }

  > div.delete-button {
    cursor: pointer;
  }

  > input {
    flex: 1 0 0;
    background-color: transparent;
    border: none;
    margin: 0;
    padding: 0;
    outline: none;
    font-size: 16px;
  }
  &:focus-within {
    border: 1px solid var(--coz-purple-600);
  }
`;

export const InputContainer = styled.div`
  background-color: #ffffff;
  display: flex;
  flex-direction: row;
  padding: 0.5rem;
  border: 1px solid rgb(223, 225, 229);
  border-radius: ${inactiveBorderRadius};
  z-index: 3;
  box-shadow: 0;

  &:focus-within {
    box-shadow: ${boxShadow};
  }
`;

export const DropDownContainer = styled.ul`
  background-color: #ffffff;
  display: block;
  margin-left: auto;
  margin-right: auto;
  list-style-type: none;
  margin-block-start: 0;
  margin-block-end: 0;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
  padding-inline-start: 0px;
  margin-top: -1px;
  padding: 0.5rem 0;
  border: 1px solid rgb(223, 225, 229);
  border-radius: 0 0 1rem 1rem;
  box-shadow: ${boxShadow};
  z-index: 3;

  > li {
    padding: 0 1rem;
    background-color: white;
  }
  > li:hover {
    padding: 0 1rem;
    background-color: gray;
  }
  > li.seleted {
    padding: 0 1rem;
    background-color: gray;
  }
`;

export const Autocomplete = ({ setTags, tags }) => {
  const [hasText, setHasText] = useState(false);
  const [inputValue, setInputValue] = useState('');
  const [options, setOptions] = useState(deselectedOptions);
  const [currentOption, seturrentOption] = useState(-1);

  // useEffect를 아래와 같이 활용할 수도 있습니다.
  useEffect(() => {
    if (inputValue === '') {
      setHasText(false);
    }
  }, [inputValue]);

  const removeTags = (indexToRemove) => {
    setTags(tags.filter((el, index) => index !== indexToRemove));
  };

  const handleKeyUp = (event) => {
    if (event.key === 'ArrowUp' && currentOption > -1) {
      seturrentOption(currentOption - 1);
    }

    if (event.key === 'ArrowDown' && currentOption < options.length - 1) {
      seturrentOption(currentOption + 1);
    }

    if (event.key === 'Enter') {
      if (tags.includes(options[currentOption])) {
        alert('중복된 태그입니다!');
        setInputValue('');
        seturrentOption(-1);
      } else if (
        !tags.includes(options[currentOption]) &&
        currentOption > -1 &&
        inputValue !== ''
      ) {
        setTags([...tags, options[currentOption]]);
        setInputValue('');
        seturrentOption(-1);
      }
      setOptions(deselectedOptions.filter((el) => el.includes(inputValue)));
    }
  };

  const handleInputChange = (event) => {
    setInputValue(event.target.value);
    setHasText(true);
    setOptions(
      deselectedOptions.filter(
        (el) =>
          event.target.value.toLowerCase() ===
          el.slice(0, event.target.value.length).toLowerCase()
      )
    );
  };

  const handleDeleteButtonClick = () => {
    setInputValue('');
    setTags([]);
    seturrentOption(-1);
  };

  return (
    <div className="autocomplete-wrapper">
      <InputContainer>
        <TagsInput>
          <ul id="tags">
            {tags.map((tag, index) => (
              <li key={index} className="tag">
                <span className="tag-title">{tag}</span>
                <span
                  className="tag-close-icon"
                  onClick={() => {
                    removeTags(index);
                  }}
                >
                  X
                </span>
              </li>
            ))}
          </ul>
          <input
            value={inputValue}
            onKeyUp={handleKeyUp}
            onChange={handleInputChange}
          ></input>
          <div className="delete-button" onClick={handleDeleteButtonClick}>
            &times;
          </div>
        </TagsInput>
      </InputContainer>
      {hasText ? (
        <DropDown
          tags={tags}
          setTags={setTags}
          currentOption={currentOption}
          options={options}
          setInputValue={setInputValue}
          seturrentOption={seturrentOption}
        />
      ) : null}
    </div>
  );
};

export const DropDown = ({
  currentOption,
  options,
  setTags,
  tags,
  setInputValue,
  seturrentOption,
}) => {
  const handleDropDownClick = (el, index) => {
    if (tags.includes(el)) {
      alert('중복된 태그입니다!');
      setInputValue('');
      seturrentOption(-1);
    } else {
      setTags([...tags, options[index]]);
      setInputValue('');
      seturrentOption(-1);
    }
  };

  return (
    <DropDownContainer>
      {options.map((el, index) => (
        <li
          className={currentOption === index ? 'seleted' : ''}
          key={index}
          onClick={() => {
            handleDropDownClick(el, index);
          }}
        >
          {el}
        </li>
      ))}
    </DropDownContainer>
  );
};
